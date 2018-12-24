package com.secondhand.tradingplatformadminservice.service.business;

import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.secondhand.tradingplatformadminentity.entity.business.Annex;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;
import com.secondhand.tradingplatformcommon.pojo.CustomizeException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 *   @description : Annex 服务接口
 *   ---------------------------------
 * 	 @author zhangjk
 *   @since 2018-12-14
 */
public interface AnnexService extends BaseService<Annex> {

        /**
         * 根据id进行假删除
         * @param annexId
         * @return
         */
        Integer myFakeDeleteById(Long annexId);

        /**
         * 根据ids进行批量假删除
         * @param annexIds
         * @return
         */
        boolean myFakeBatchDelete(List<Long> annexIds);

        /**
         * 获取Map数据（Obj）
         * @param annexId
         * @return
         */
        Map<String, Object> mySelectMapById(Long annexId);

        /**
         * 新增或修改annex
         * @param annex
         * @return
         */
        Annex myAnnexCreateUpdate(Annex annex);

        /**
         * 附件上传
         * @param resourceType
         * @param description
         * @param md5value
         * @param chunks
         * @param chunk
         * @param name
         * @param file
         * @return
         * @throws CustomizeException
         */
        Annex myAnnexUpload(String resourceType,String description,String md5value,String chunks,String chunk,String name,MultipartFile file) throws CustomizeException;

        /**
         * 根据md5值判断该附件是否存在
         * @param md5value
         * @return
         */
        Annex myGetAnnexExistsByMd5(String md5value);

        /**
         * 根据附件id获取图片
         * @param annexId
         * @param response
         * @throws IOException
         * @throws CustomizeException
         */
        void myGetImageByAnnexId(Long annexId,HttpServletResponse response) throws IOException, CustomizeException;

        /**
         * 普通文件下载
         * @param request
         * @param response
         * @param annexId
         * @throws CustomizeException
         * @throws IOException
         */
        void myDownloadFile(HttpServletRequest request, HttpServletResponse response, Long annexId) throws CustomizeException, IOException;

        /**
         * 大文件下载
         * @param request
         * @param response
         * @param annexId
         * @throws CustomizeException
         * @throws IOException
         */
        void myDownloadLargeFile(HttpServletRequest request, HttpServletResponse response, Long annexId) throws CustomizeException, IOException;

        /**
         * 分页获取Annex列表数据（实体类）
         * @param page
         * @param annex
         * @return
         */
        Page<Annex> mySelectPageWithParam(Page<Annex> page, Annex annex);

        /**
         * 获取Annex列表数据（Map）
         * @param map
         * @return
         */
        List<Annex> mySelectListWithMap(Map<String, Object> map);

        /**
         * 根据wrapper获取map
         * @param wrapper
         * @return
         */
        Map<String, Object> mySelectMap(Wrapper<Annex> wrapper);

        /**
         * 根据wrapper获取List
         * @param wrapper
         * @return
         */
        List<Annex> mySelectList(Wrapper<Annex> wrapper);

        /**
         * 插入Annex
         * @param annex
         * @return
         */
        boolean myInsert(Annex annex);

        /**
         * 批量插入List<Annex>
         * @param annexList
         * @return
         */
        boolean myInsertBatch(List<Annex> annexList);

        /**
         * 插入或更新annex
         * @param annex
         * @return
         */
        boolean myInsertOrUpdate(Annex annex);

        /**
         * 批量插入或更新List<Annex>
         * @param annexList
         * @return
         */
        boolean myInsertOrUpdateBatch(List<Annex> annexList);

        /**
         * 根据annexIds获取List
         * @param annexIds
         * @return
         */
        List<Annex> mySelectBatchIds(Collection<? extends Serializable> annexIds);

        /**
         * 根据annexId获取Annex
         * @param annexId
         * @return
         */
        Annex mySelectById(Serializable annexId);

        /**
         * 根据wrapper查找Count
         * @param wrapper
         * @return
         */
        int mySelectCount(Wrapper<Annex> wrapper);

        /**
         * 根据wrapper获取Annex
         * @param wrapper
         * @return
         */
        Annex mySelectOne(Wrapper<Annex> wrapper);

        /**
         * 根据annex和wrapper更新annex
         * @param annex
         * @param wrapper
         * @return
         */
        boolean myUpdate(Annex annex, Wrapper<Annex> wrapper);

        /**
         * 根据annexList更新annex
         * @param annexList
         * @return
         */
        boolean myUpdateBatchById(List<Annex> annexList);

        /**
         * 根据annexId修改annex
         * @param annex
         * @return
         */
        boolean myUpdateById(Annex annex);

}
