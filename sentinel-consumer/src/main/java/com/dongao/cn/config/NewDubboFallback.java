package com.dongao.cn.config;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallback;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcResult;

/**
 * @author jiabing
 * @Package com.dongao.cn.config
 * @Description: ${todo}
 * @date 2018/12/11 10:03
 */
public class NewDubboFallback implements DubboFallback {


    @Override
    public Result handle(Invoker<?> invoker, Invocation invocation, BlockException e) {

        return new RpcResult("dubbo error");
    }


}