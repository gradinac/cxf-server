package com.cxf.server;

import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.bus.extension.ExtensionManagerBus;
import org.apache.cxf.common.spi.GeneratedNamespaceClassLoader;
import org.apache.cxf.common.spi.NamespaceClassCreator;
import org.apache.cxf.endpoint.dynamic.ExceptionClassCreator;
import org.apache.cxf.endpoint.dynamic.ExceptionClassLoader;
import org.apache.cxf.jaxb.FactoryClassCreator;
import org.apache.cxf.jaxb.FactoryClassLoader;
import org.apache.cxf.jaxb.WrapperHelperClassLoader;
import org.apache.cxf.jaxb.WrapperHelperCreator;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.spi.WrapperClassCreator;
import org.apache.cxf.jaxws.spi.WrapperClassLoader;
import org.apache.cxf.wsdl.ExtensionClassCreator;
import org.apache.cxf.wsdl.ExtensionClassLoader;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class ApplicationConfig {

    @Autowired
    private Bus bus;

    @Bean
    public Endpoint endpoint(Bus bus) {
        bus.setExtension(new WrapperHelperClassLoader(bus), WrapperHelperCreator.class);
        bus.setExtension(new ExtensionClassLoader(bus), ExtensionClassCreator.class);
        bus.setExtension(new ExceptionClassLoader(bus), ExceptionClassCreator.class);
        bus.setExtension(new WrapperClassLoader(bus), WrapperClassCreator.class);
        bus.setExtension(new FactoryClassLoader(bus), FactoryClassCreator.class);
        bus.setExtension(new GeneratedNamespaceClassLoader(bus), NamespaceClassCreator.class);

        EndpointImpl endpoint =
                new EndpointImpl(bus, new HelloWorldImpl());
        endpoint.publish("/helloworld");

        return endpoint;
    }
}