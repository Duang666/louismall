package com.louisblogs.louismall.ware.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.louisblogs.common.utils.PageUtils;
import com.louisblogs.louismall.ware.entity.UndoLogEntity;

import java.util.Map;

/**
 * 
 *
 * @author luqi
 * @email lq844040753@163.com
 * @date 2021-05-17 17:29:32
 */
public interface UndoLogService extends IService<UndoLogEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

