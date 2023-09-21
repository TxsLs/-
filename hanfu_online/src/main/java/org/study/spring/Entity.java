package org.study.spring;


import org.quincy.rock.core.dao.annotation.PrimaryKey;
import org.quincy.rock.core.vo.BaseEntity;

import io.swagger.annotations.ApiModelProperty;

/**
 * <b>实体基类。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 无。
 * 
 * @version 1.0
 * @author mex2000
 * @since 1.0
 */
public abstract class Entity extends BaseEntity<Long> {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -9146290789116371771L;

	/**
	 * 主键id。
	 */
	@ApiModelProperty(value="主键id",position = 0)
	@PrimaryKey("f_id")
	private Long id;

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id()
	 */
	@Override
	public Long id() {
		return id;
	}

	/** 
	 * id。
	 * @see org.quincy.rock.core.vo.Vo#id(java.lang.Object)
	 */
	@Override
	public Entity id(Long id) {
		this.id = id;
		return this;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
