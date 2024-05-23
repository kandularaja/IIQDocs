package com.example.integration;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import sailpoint.object.Schema;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sailpoint.object.Application;
import sailpoint.object.ProvisioningResult;
import sailpoint.object.Rule;
import sailpoint.object.ProvisioningPlan;
import sailpoint.object.ProvisioningPlan.AttributeRequest;
import sailpoint.object.IntegrationConfig;
import sailpoint.api.SailPointContext;
import sailpoint.integration.AbstractIntegrationExecutor;

public class ProvisionIntegrMod extends AbstractIntegrationExecutor
{
    private SailPointContext context;
	private String ruleName;
    
    public void configure(final SailPointContext context, final IntegrationConfig config) throws Exception {
        super.configure(this.context = context, config);
        this.ruleName = (String) config.getAttribute("ruleName");
    }
    
    public ProvisioningResult provision(final ProvisioningPlan plan) throws Exception
	{
        ProvisioningResult provResult = new ProvisioningResult();
    	if (null != plan && null != context) {
    	       
    	       
            
            Map ruleArgs = new HashMap();
            ruleArgs.put("plan", plan);
           
            Rule rule = context.getObjectByName(Rule.class, ruleName);
            
            if(rule == null) {
            	provResult.setStatus(ProvisioningResult.STATUS_FAILED);
            }else {
            	provResult = (ProvisioningResult) context.runRule(rule, ruleArgs);
            }
           
        } else {
        	provResult.setStatus(ProvisioningResult.STATUS_FAILED);
        	provResult.addError("The context or plan was null");
        }
       
        return provResult;
    } 


	}
