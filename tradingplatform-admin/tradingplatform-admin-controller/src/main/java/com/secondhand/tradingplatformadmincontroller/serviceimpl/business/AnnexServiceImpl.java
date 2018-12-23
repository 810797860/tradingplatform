package com.secondhand.tradingplatformadmincontroller.serviceimpl.business;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.business.Annex;
import com.secondhand.tradingplatformadminmapper.mapper.business.AnnexMapper;
import com.secondhand.tradingplatformadminservice.service.business.AnnexService;
import com.secondhand.tradingplatformcommon.base.BaseEntity.Sort;
import com.secondhand.tradingplatformcommon.base.BaseServiceImpl.BaseServiceImpl;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
import com.secondhand.tradingplatformcommon.pojo.CustomizeStatus;
import com.secondhand.tradingplatformcommon.pojo.MagicalValue;
import com.secondhand.tradingplatformcommon.util.ToolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *   @description : Annex 服务实现类
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-14
 */

@Service
@CacheConfig(cacheNames = "annex")
public class AnnexServiceImpl extends BaseServiceImpl<AnnexMapper, Annex> implements AnnexService {

    @Value("${tradingplatform.file.upload.path}")
    private String fileUploadLath;

    @Autowired
    private AnnexMapper annexMapper;

    private final static List<Annex> uploadInfoList = new ArrayList<>();

    @Override
    @CacheEvict(allEntries = true)
    public Integer myFakeDeleteById(Long annexId) {
        Annex annex = new Annex();
        annex.setId(annexId);
        annex.setDeleted(true);
        return annexMapper.updateById(annex);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public boolean myFakeBatchDelete(List<Long> annexIds) {
        annexIds.forEach(annexId->{
            myFakeDeleteById(annexId);
        });
        return true;
    }

    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMapById(Long annexId) {
        return annexMapper.selectMapById(annexId);
    }

    @Override
    @CacheEvict(allEntries = true)
    public Annex myAnnexCreateUpdate(Annex annex) {
        Long annexId = annex.getId();
        if (annexId == null){
            annex.setUuid(ToolUtil.getUUID());
            annexMapper.insert(annex);
        } else {
            annexMapper.updateById(annex);
        }
        return annex;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CacheEvict(allEntries = true)
    public Annex myAnnexUpload(String resourceType, String description, String md5value, String chunks, String chunk, String name, MultipartFile file) throws CustomizeException {

        //根据日期决定上传文件的路径
        String uploadFolderPath = fileUploadLath + ToolUtil.getToday();
        //判断该路径的文件夹是否存在
        //暂时先每次判断，应该要定时业务更新
        File uploadFolderPathFile = new File(uploadFolderPath);
        if (uploadFolderPathFile.exists()){
            uploadFolderPathFile.mkdir();
        }
        //存进去
        Annex annex = new Annex();
        annex.setDescription(description);
        annex.setName(name);
        annex.setType(resourceType);

        if (file == null){

            //之前已经提交过
            if (md5value == null){
                throw new CustomizeException(CustomizeStatus.MD5_VALUE_IS_EMPTY, this.getClass());
            }

            //根据Md5值找之前提交了的文件
            Wrapper<Annex> oldWrapper = new EntityWrapper<>();
            //找最新的md5值一样的那一条
            oldWrapper.where("md5 = {0}", md5value);
            oldWrapper.where("deleted = {0}", false);
            oldWrapper.last("limit 1");
            List<Annex> oldAnnexList = annexMapper.selectList(oldWrapper);
            //判空
            if (oldAnnexList == null) {
                throw new CustomizeException(CustomizeStatus.IMAGE_HAS_NO_SUBMIT, this.getClass());
            }
            //写入到数据库
            annex.setUuid(ToolUtil.getUUID());
            annexMapper.insert(annex);
        } else {
            annex = fileUpload(name, md5value, chunks, chunk, name, file, uploadFolderPath);
            if (annex != null){
                annex.setType("resource");
                annex.setUuid(ToolUtil.getUUID());
                annexMapper.insert(annex);
            }
        }
        return annex;
    }

    @Override
    @Cacheable(key = "#p0")
    public Annex myGetAnnexExistsByMd5(String md5value) {
        Annex annex = new Annex();
        annex.setMd5(md5value);
        return annexMapper.selectOne(annex);
    }

    @Override
    public void myGetImageByAnnexId(Long annexId, HttpServletResponse response) throws IOException, CustomizeException {
        ///static/default.png默认图片
        InputStream is = this.getClass().getResourceAsStream("/static/default.png");
        if (annexId != null){
            Annex annex = mySelectById(annexId);
            if (annex != null){
                responseImage(response, annex, is);
            } else {
                responseImage(response, null, is);
            }
        }else {
            responseImage(response, null, is);
        }
    }

    /**
     * 文件上传
     * @param temporaryName
     * @param md5value
     * @param chunks
     * @param chunk
     * @param name
     * @param file
     * @param uploadFolderPath
     * @return
     * @throws CustomizeException
     */
    @Transactional(rollbackFor = Exception.class)
    public Annex fileUpload(String temporaryName, String md5value, String chunks, String chunk, String name, MultipartFile file, String uploadFolderPath) throws CustomizeException {

        String fileName = "";
        int temporaryNameIndex = temporaryName.indexOf(MagicalValue.DECIMAL_POINT);
        //获取简单temporaryName
        if (temporaryNameIndex != -1){
            temporaryName = temporaryName.substring(0, temporaryNameIndex) + MagicalValue.DIVIDING_LINE + ToolUtil.getMinutesAndSeconds();
        }

        try{

            //合并路径
            String combinePath = uploadFolderPath + File.separator + temporaryName + MagicalValue.FILE_SEPARATOR;
            //文件后缀名
            String suffix = "";
            if (name.lastIndexOf(MagicalValue.DECIMAL_POINT) != -1){
                suffix = name.substring(name.lastIndexOf(MagicalValue.DECIMAL_POINT));
            }

            //判断文件是否分块
            if (chunks != null && chunk != null && !(MagicalValue.STRING_OF_ONE).equals(chunk)){
                fileName = chunk + suffix;
                saveFile(combinePath, fileName, file);

                //验证是否上传成功 + 合并
                Annex annex = uploaded(md5value, temporaryName, chunk, chunks, uploadFolderPath, fileName, suffix);
                return annex;
            }else {
                //不是分块上传，直接保存
                fileName = temporaryName + suffix;
                saveFile(uploadFolderPath + File.separator, fileName, file);
                Annex annex = new Annex();
                annex.setName(file.getName());
                annex.setExtension(suffix);
                annex.setSize(Float.parseFloat(Long.toString(file.getSize())));
                annex.setContentType(file.getContentType());
                annex.setPath(uploadFolderPath + File.separator + file.getName());
                annex.setMd5(md5value);
                return annex;
            }
        }catch (Exception e){
            e.printStackTrace();
            throw new CustomizeException(CustomizeStatus.UPLOADING_IMAGE_FAILED, this.getClass());
        }
    }

    /**
     * 保存文件
     * @param savePath
     * @param fileFullName
     * @param file
     * @return
     * @throws IOException
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean saveFile(String savePath, String fileFullName, MultipartFile file) throws IOException {
        byte[] data = readInputStream(file.getInputStream());
        File uploadFile = new File(savePath + fileFullName);
        //判断文件夹是否存在，不存在就创建一个
        File uploadFileDirectory = new File(savePath);
        if ( !uploadFileDirectory.exists() ){
            uploadFileDirectory.mkdir();
        }

        //创建文件输出流
        try (FileOutputStream outputStream = new FileOutputStream(uploadFile)){
            outputStream.write(data);
            outputStream.flush();
        } catch (Exception e){
            e.printStackTrace();
            throw e;
        }

        return uploadFile.exists();
    }

    /**
     * 读取输入流
     * @param inputStream
     * @return
     * @throws IOException
     */
    public byte[] readInputStream(InputStream inputStream) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //创建一个buffer字符串
        byte[] buffer = new byte[1024];
        //用buffer读取inputStream
        //字符串长度为1，时，则全部读取完毕
        int length = 0;
        while ((length = inputStream.read(buffer)) != -1){
            outputStream.write(buffer, 0 , length);
        }
        inputStream.close();
        return outputStream.toByteArray();
    }

    /**
     * 底层上传代码
     * @param temporaryName
     * @param md5value
     * @param chunks
     * @param chunk
     * @param uploadFolderPath
     * @param name
     * @param suffix
     * @return
     * @throws IOException
     */
    public Annex uploaded(String temporaryName, String md5value, String chunks, String chunk, String uploadFolderPath, String name, String suffix) throws IOException {
        Annex annex = new Annex();
        synchronized (uploadInfoList){
            annex.setMd5(md5value);
            annex.setName(name);
            annex.setExtension(suffix);
            uploadInfoList.add(annex);
        }

        boolean uploadedFlag = isAllUploaded(md5value, chunks);
        int chunksNum = Integer.valueOf(chunks);

        if (uploadedFlag){
            String totalPath = mergeFile(chunksNum, suffix, temporaryName, uploadFolderPath);
            File totalFile = new File(totalPath);
            annex.setName(totalFile.getName());
            annex.setSize(Float.parseFloat(Long.toString(totalFile.length())));
            annex.setPath(totalPath);
            return annex;
        }else {
            return null;
        }
    }

    /**
     * 去重
     * @param md5
     * @param chunks
     * @return
     */
    public boolean isAllUploaded(String md5, String chunks){

        int size = uploadInfoList.stream()
                .filter(item -> item.getMd5().equals(md5))
                .distinct()
                .collect(Collectors.toList())
                .size();

        boolean flag = size == Integer.valueOf(chunks);
        if (flag){
            synchronized (uploadInfoList){
                uploadInfoList.removeIf(item -> item.getMd5().equals(md5));
            }
        }
        return flag;
    }

    /**
     * 合并文件
     * @param chunksNum
     * @param suffix
     * @param temporaryName
     * @param uploadFolderPath
     * @return
     * @throws IOException
     */
    public String mergeFile(int chunksNum, String suffix, String temporaryName, String uploadFolderPath) throws IOException {

        String mergePath = uploadFolderPath + File.separator + temporaryName + MagicalValue.FILE_SEPARATOR;
        InputStream inputStream1 = new FileInputStream(mergePath + 1 + suffix);
        InputStream inputStream2 = new FileInputStream(mergePath + 2 + suffix);
        //输入流逻辑串联
        SequenceInputStream sequenceInputStream = new SequenceInputStream(inputStream1, inputStream2);
        for (int i = 3; i <= chunksNum; i++){
            InputStream inputStream3 = new FileInputStream(mergePath + 3 + suffix);
            sequenceInputStream = new SequenceInputStream(sequenceInputStream, inputStream3);
        }
        //最终路径
        String totalPath = uploadFolderPath + File.separator + temporaryName + MagicalValue.DIVIDING_LINE + ToolUtil.getMinutesAndSeconds() + suffix;
        //从inputStream保存文件
        saveStreamToFile(sequenceInputStream, totalPath);
        //删除分块文件+文件夹
        deleteFolder(mergePath);
        return totalPath;
    }

    /**
     * 从inputStream保存文件
     * @param inputStream
     * @param filePath
     * @throws IOException
     */
    @Transactional(rollbackFor = Exception.class)
    public void saveStreamToFile(InputStream inputStream, String filePath) throws IOException {
        //创建输出流，并合并分块文件
        OutputStream outputStream = new FileOutputStream(filePath);
        byte[] data = new byte[1024];
        int length = 0;
        try{
            while ((length = inputStream.read(data)) != -1){
                outputStream.write(data, 0, length);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            outputStream.close();
            inputStream.close();
        }
    }

    /**
     * 删除指定文件夹
     * @param filePath
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteFolder(String filePath){

        File directory = new File(filePath);
        //先删掉文件夹里面的文件
        File[] files = directory.listFiles();
        if (files != null){
            for (File file : files){
                try {
                    file.delete();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        //再删掉该文件/文件夹
        return directory.delete();
    }

    /**
     * 显示图片
     * @param response
     * @param annex
     * @param stream
     * @throws CustomizeException
     * @throws IOException
     */
    @Transactional(rollbackFor = Exception.class)
    public void responseImage(HttpServletResponse response, Annex annex, InputStream stream) throws CustomizeException, IOException {

        String path = null;
        //消息头默认为multipart/form-data
        String contentType = "multipart/form-data";
        String filename = null;
        String extension = null;
        //区别显示默认图片的情况
        if (annex != null){
            path = annex.getPath();
            filename = annex.getName();
            extension = annex.getExtension();
        }

        InputStream inputStream;
        OutputStream outputStream = null;

        if (path == null || path.trim().equals("")){
            inputStream = stream;
            response.setContentType(contentType);
        } else {
            File file = new File(path);
            if (file.exists()){
                response.setContentType(contentType);
                //拼接名字- XXX.jpg
                response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"), "ISO8859-1") + MagicalValue.DECIMAL_POINT + extension);
                response.addHeader("Content-Length", "" + file.length());
                try {
                    inputStream = new FileInputStream(file);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    throw new CustomizeException(CustomizeStatus.IMAGE_HAS_NO_SUBMIT, ToolUtil.class);
                }
            } else {
                inputStream = stream;
                response.setContentType(contentType);
            }
        }

        try{
            outputStream = response.getOutputStream();
            int index = 0;
            byte[] data = new byte[1024];
            while ((index = inputStream.read(data)) != -1){
                outputStream.write(data, 0, index);
            }
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            inputStream.close();
            outputStream.close();
        }
    }

    //以下是继承BaseServiceImpl
    
    @Override
    @Cacheable(key = "#p0 + ',' + #p1 + ',' + #p1.sorts")
    public Page<Annex> mySelectPageWithParam(Page<Annex> page, Annex annex) {

        //判空
        annex.setDeleted(false);
        Wrapper<Annex> wrapper = new EntityWrapper<>(annex);
        //遍历排序
        List<Sort> sorts = annex.getSorts();
        if (sorts == null){
            //为null时，默认按created_at倒序
            wrapper.orderBy("id", false);
        } else {
            //遍历排序
            sorts.forEach( sort -> {
                wrapper.orderBy(sort.getField(), sort.getAsc());
            });
        }
        return this.selectPage(page, wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Annex> mySelectListWithMap(Map<String, Object> map) {
        return annexMapper.selectByMap(map);
    }
    
    //以下是继承BaseMapper
    
    @Override
    @Cacheable(key = "#p0")
    public Map<String, Object> mySelectMap(Wrapper<Annex> wrapper) {
        return this.selectMap(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Annex> mySelectList(Wrapper<Annex> wrapper) {
        return annexMapper.selectList(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsert(Annex annex) {
        annex.setUuid(ToolUtil.getUUID());
        return this.insert(annex);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertBatch(List<Annex> annexList) {
        annexList.forEach(annex -> {
            annex.setUuid(ToolUtil.getUUID());
        });
        return this.insertBatch(annexList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdate(Annex annex) {
        //没有uuid的话要加上去
        if (annex.getUuid().equals(null)){
            annex.setUuid(ToolUtil.getUUID());
        }
        return this.insertOrUpdate(annex);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myInsertOrUpdateBatch(List<Annex> annexList) {
        annexList.forEach(annex -> {
            //没有uuid的话要加上去
            if (annex.getUuid().equals(null)){
                annex.setUuid(ToolUtil.getUUID());
            }
        });
        return this.insertOrUpdateBatch(annexList);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public List<Annex> mySelectBatchIds(Collection<? extends Serializable> annexIds) {
        return annexMapper.selectBatchIds(annexIds);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Annex mySelectById(Serializable annexId) {
        return annexMapper.selectById(annexId);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public int mySelectCount(Wrapper<Annex> wrapper) {
        return annexMapper.selectCount(wrapper);
    }
    
    @Override
    @Cacheable(key = "#p0")
    public Annex mySelectOne(Wrapper<Annex> wrapper) {
        return this.selectOne(wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdate(Annex annex, Wrapper<Annex> wrapper) {
        return this.update(annex, wrapper);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateBatchById(List<Annex> annexList) {
        return this.updateBatchById(annexList);
    }
    
    @Override
    @CacheEvict(allEntries = true)
    public boolean myUpdateById(Annex annex) {
        return this.updateById(annex);
    }
}
