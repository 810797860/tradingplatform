package com.secondhand.tradingplatformadminservice.service.front.personal;

import com.secondhand.tradingplatformadminentity.entity.front.personal.MyComment;
import com.secondhand.tradingplatformcommon.base.BaseService.BaseService;

import java.util.List;
import java.util.Map;

/**
 * @author zhangjk
 * @description : MyComment 服务接口
 * ---------------------------------
 * @since 2019-03-15
 */
public interface MyCommentService extends BaseService<MyComment>{

    /**
     * 获取数据总量
     * @param myComment
     * @return
     */
    Long mySelectTotalWithParam(MyComment myComment);

    /**
     * 分页获取MyComment列表数据（实体类）
     * @param myComment
     * @param current
     * @param size
     * @return
     */
    List<Map<String, Object>> mySelectSaleListWithParam(MyComment myComment, int current, int size);

    /**
     * 获取数据总量
     * @param myComment
     * @return
     */
    Long mySelectSaleTotalWithParam(MyComment myComment);

    /**
     * 分页获取MyComment列表数据（实体类）
     * @param myComment
     * @param current
     * @param size
     * @return
     */
    List<Map<String, Object>> mySelectListWithParam(MyComment myComment, int current, int size);
}
/*
SELECT
	*
FROM
	(
		SELECT
			cbea.id AS id,
			cbeae.id AS evaluation_id,
			cbea.title AS title,
			cbea.cover AS cover,
			cbeae.content AS content,
			cbeae.star AS star,
			(
				SELECT
					concat(
						'{"id":"',
						sbu.id,
						'","user_name":"',
						sbu.user_name,
						'"}'
					)
				FROM
					s_base_user sbu
				WHERE
					(sbu.id = cbeae.user_id)
			) AS user_id,
			(
				SELECT
					concat(
						'{"id":"',
						sbu.id,
						'","user_name":"',
						sbu.user_name,
						'"}'
					)
				FROM
					s_base_user sbu
				WHERE
					(sbu.id = cbea.user_id)
			) AS sale_user,
			200120 AS category,
			cbeae.created_at AS created_at
		FROM
			c_business_electric_appliance_evaluation cbeae
		JOIN c_business_electric_appliance cbea ON (
			cbeae.electric_id = cbea.id
			AND cbeae.user_id = 48
			AND cbeae.back_check_status = 100509
			AND cbea.deleted = FALSE
			AND cbeae.deleted = FALSE
		)
		UNION
			SELECT
				cbbl.id AS id,
				cbble.id AS evaluation_id,
				cbbl.title AS title,
				cbbl.cover AS cover,
				cbble.content AS content,
				cbble.star AS star,
				(
					SELECT
						concat(
							'{"id":"',
							sbu.id,
							'","user_name":"',
							sbu.user_name,
							'"}'
						)
					FROM
						s_base_user sbu
					WHERE
						(sbu.id = cbble.user_id)
				) AS user_id,
				(
					SELECT
						concat(
							'{"id":"',
							sbu.id,
							'","user_name":"',
							sbu.user_name,
							'"}'
						)
					FROM
						s_base_user sbu
					WHERE
						(sbu.id = cbbl.user_id)
				) AS sale_user,
				200121 AS category,
				cbble.created_at AS created_at
			FROM
				c_business_book_library_evaluation cbble
			JOIN c_business_book_library cbbl ON (
				cbble.book_id = cbbl.id
				AND cbble.user_id = 48
				AND cbble.back_check_status = 100509
				AND cbbl.deleted = FALSE
				AND cbble.deleted = FALSE
			)
			UNION
				SELECT
					cbss.id AS id,
					cbsse.id AS evaluation_id,
					cbss.title AS title,
					cbss.cover AS cover,
					cbsse.content AS content,
					cbsse.star AS star,
					(
						SELECT
							concat(
								'{"id":"',
								sbu.id,
								'","user_name":"',
								sbu.user_name,
								'"}'
							)
						FROM
							s_base_user sbu
						WHERE
							(sbu.id = cbsse.user_id)
					) AS user_id,
					(
						SELECT
							concat(
								'{"id":"',
								sbu.id,
								'","user_name":"',
								sbu.user_name,
								'"}'
							)
						FROM
							s_base_user sbu
						WHERE
							(sbu.id = cbss.user_id)
					) AS sale_user,
					200122 AS category,
					cbsse.created_at AS created_at
				FROM
					c_business_sports_special_evaluation cbsse
				JOIN c_business_sports_special cbss ON (
					cbsse.sports_id = cbss.id
					AND cbsse.user_id = 48
					AND cbsse.back_check_status = 100509
					AND cbss.deleted = FALSE
					AND cbsse.deleted = FALSE
				)
				UNION
					SELECT
						cbds.id AS id,
						cbdse.id AS evaluation_id,
						cbds.title AS title,
						cbds.cover AS cover,
						cbdse.content AS content,
						cbdse.star AS star,
						(
							SELECT
								concat(
									'{"id":"',
									sbu.id,
									'","user_name":"',
									sbu.user_name,
									'"}'
								)
							FROM
								s_base_user sbu
							WHERE
								(sbu.id = cbdse.user_id)
						) AS user_id,
						(
							SELECT
								concat(
									'{"id":"',
									sbu.id,
									'","user_name":"',
									sbu.user_name,
									'"}'
								)
							FROM
								s_base_user sbu
							WHERE
								(sbu.id = cbds.user_id)
						) AS sale_user,
						200123 AS category,
						cbdse.created_at AS created_at
					FROM
						c_business_digital_square_evaluation cbdse
					JOIN c_business_digital_square cbds ON (
						cbdse.digital_id = cbds.id
						AND cbdse.user_id = 48
						AND cbdse.back_check_status = 100509
						AND cbds.deleted = FALSE
						AND cbdse.deleted = FALSE
					)
					UNION
						SELECT
							cbrh.id AS id,
							cbrhe.id AS evaluation_id,
							cbrh.title AS title,
							cbrh.cover AS cover,
							cbrhe.content AS content,
							cbrhe.star AS star,
							(
								SELECT
									concat(
										'{"id":"',
										sbu.id,
										'","user_name":"',
										sbu.user_name,
										'"}'
									)
								FROM
									s_base_user sbu
								WHERE
									(sbu.id = cbrhe.user_id)
							) AS user_id,
							(
								SELECT
									concat(
										'{"id":"',
										sbu.id,
										'","user_name":"',
										sbu.user_name,
										'"}'
									)
								FROM
									s_base_user sbu
								WHERE
									(sbu.id = cbrh.user_id)
							) AS sale_user,
							200124 AS category,
							cbrhe.created_at AS created_at
						FROM
							c_business_renting_house_evaluation cbrhe
						JOIN c_business_renting_house cbrh ON (
							cbrhe.renting_id = cbrh.id
							AND cbrhe.user_id = 48
							AND cbrhe.back_check_status = 100509
							AND cbrh.deleted = FALSE
							AND cbrhe.deleted = FALSE
						)
	) myDual
ORDER BY
	myDual.created_at DESC
LIMIT 0,
 20
*/