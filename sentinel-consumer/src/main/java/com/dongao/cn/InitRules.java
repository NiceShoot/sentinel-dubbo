package com.dongao.cn;

import com.alibaba.csp.sentinel.adapter.dubbo.fallback.DubboFallbackRegistry;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.dongao.cn.config.NewDubboFallback;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiabing
 * @Package com.dongao.sentinel
 * @Description: ${todo}
 * @date 2018/11/29 14:28
 */
@Component
public class InitRules implements ApplicationListener<ContextRefreshedEvent> {

    final String remoteAddress = "192.168.56.101:2181";
    final String path = "/Sentinel-Demo/SYSTEM-CODE-DEMO-FLOW";
    private static final int RETRY_TIMES = 3;
    private static final int SLEEP_TIME = 1000;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //控流规则
        this.initFlowQpsRule();

        //降级规则
        this.initFlowDeRule();

        //系统保护
        //this.initSystemRule();

        //启动zk监听
        //this.nodeChangeListen();

        //配置dubbofallback
        DubboFallbackRegistry.setConsumerFallback(new NewDubboFallback());
    }


    /**
     * 监听zk，修改规则
     */
    public void nodeChangeListen(){
        CuratorFramework zkClient = CuratorFrameworkFactory.newClient(remoteAddress, new ExponentialBackoffRetry(SLEEP_TIME, RETRY_TIMES));
        zkClient.start();
        final NodeCache nodeCache = new NodeCache(zkClient, path, false);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                initFlowQpsRule();
            }
        });
    }




    /**
     * 控流规则
     */
    private void initFlowQpsRule() {
        //获取zk中的规则
        /*ReadableDataSource<String, List<FlowRule>> flowRuleDataSource = new ZookeeperDataSource<>(remoteAddress,
                path,
                new Converter<String, List<FlowRule>>() {
                    @Override
                    public List<FlowRule> convert(String s) {
                        List<FlowRule> flowRules = JSON.parseObject(s, new TypeReference<List<FlowRule>>() {
                        });
                        List<Map> maps = JSONObject.parseArray(s, Map.class);
                        if(flowRules!=null && maps!=null){
                            for(int i =0;i<flowRules.size();i++){
                                FlowRule flowRule = flowRules.get(i);
                                Map map = maps.get(i);
                                if(map.get("resource")!=null)
                                    flowRule.setResource(map.get("resource").toString());
                                if (map.get("limitApp")!=null)
                                    flowRule.setLimitApp(map.get("limitApp").toString());
                            }
                        }
                        return flowRules;
                    }
                });
        FlowRuleManager.register2Property(flowRuleDataSource.getProperty());*/

        List<FlowRule> rules = new ArrayList<FlowRule>();
        FlowRule rule1 = new FlowRule();
        rule1.setResource("com.dongao.cn.service.TestRemoteService:getName()");
        //set threshold rt, 10 ms
        rule1.setCount(4);
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 降级规则
      */
    private void initFlowDeRule() {
        //服务降级之后 该时间长度内  会直接返回（s）
        Integer TIME_WINDOW = 3;
        //平均响应时间（ms）
        Integer DEGRADE_GRADE_RT = 1;
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule1 = new DegradeRule();
        rule1.setResource("hello_2");
        //set threshold rt, 10 ms
        rule1.setCount(DEGRADE_GRADE_RT);
        rule1.setTimeWindow(TIME_WINDOW);
        rule1.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        rules.add(rule1);
        DegradeRuleManager.loadRules(rules);
    }


    /**
     * 系统保护规则
     */
    private void initSystemRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        rule.setHighestSystemLoad(3);
        rule.setMaxThread(3);
        rule.setQps(2);
        rule.setAvgRt(2);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }
}