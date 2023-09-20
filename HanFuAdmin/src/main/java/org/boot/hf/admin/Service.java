package org.boot.hf.admin;

import org.quincy.rock.core.dao.IService;

/**
 * <b>业务基接口。</b>
 * <p><b>详细说明：</b></p>
 * <!-- 在此添加详细说明 -->
 * 业务接口尽可能从该基接口继承。
 * @version 1.0
 * @author mex2000
 * @since 1.0
 */
public interface Service<T extends Entity> extends IService<T> {

}

