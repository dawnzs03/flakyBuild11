package com.test.insertion;

import com.jsql.model.InjectionModel;
import com.jsql.model.exception.JSqlException;
import com.jsql.view.terminal.SystemOutTerminal;
import com.test.vendor.mysql.ConcreteMySqlSuiteIT;
import org.apache.commons.lang3.StringUtils;
import org.junitpioneer.jupiter.RetryingTest;

import java.util.AbstractMap.SimpleEntry;
import java.util.Arrays;

public class EmptyNormalSuiteIT extends ConcreteMySqlSuiteIT {
    
    @Override
    public void setupInjection() throws Exception {
        
        InjectionModel model = new InjectionModel();
        this.injectionModel = model;

        model.subscribe(new SystemOutTerminal());

        model.getMediatorUtils().getParameterUtil().initializeQueryString("http://localhost:8080/insertion-char");
        model.getMediatorUtils().getParameterUtil().setListQueryString(Arrays.asList(
            new SimpleEntry<>("tenant", "mysql"),
            new SimpleEntry<>("name", StringUtils.EMPTY)
        ));
        
        model.setIsScanning(true);
        
        model
        .getMediatorUtils()
        .getConnectionUtil()
        .withMethodInjection(model.getMediatorMethod().getQuery())
        .withTypeRequest("GET");
        
        model.beginInjection();
    }
    
    @Override
    @RetryingTest(3)
    public void listDatabases() throws JSqlException {
        super.listDatabases();
    }
}
