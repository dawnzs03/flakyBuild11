package io.quarkus.agroal.runtime;

import java.util.function.Function;
import java.util.function.Supplier;

import io.agroal.api.AgroalDataSource;
import io.quarkus.arc.SyntheticCreationalContext;
import io.quarkus.datasource.runtime.DataSourcesRuntimeConfig;
import io.quarkus.runtime.annotations.Recorder;

@Recorder
public class AgroalRecorder {

    public Supplier<DataSourceSupport> dataSourceSupportSupplier(DataSourceSupport dataSourceSupport) {
        return new Supplier<DataSourceSupport>() {
            @Override
            public DataSourceSupport get() {
                return dataSourceSupport;
            }
        };
    }

    public Function<SyntheticCreationalContext<AgroalDataSource>, AgroalDataSource> agroalDataSourceSupplier(
            String dataSourceName,
            @SuppressWarnings("unused") DataSourcesRuntimeConfig dataSourcesRuntimeConfig) {
        return new Function<>() {
            @Override
            public AgroalDataSource apply(SyntheticCreationalContext<AgroalDataSource> context) {
                DataSources dataSources = context.getInjectedReference(DataSources.class);
                return dataSources.getDataSource(dataSourceName);
            }
        };
    }
}
