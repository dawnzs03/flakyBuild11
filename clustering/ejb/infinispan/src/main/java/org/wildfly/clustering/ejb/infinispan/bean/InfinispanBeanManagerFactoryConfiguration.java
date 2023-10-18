/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2014, Red Hat, Inc., and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.wildfly.clustering.ejb.infinispan.bean;

import org.infinispan.remoting.transport.Address;
import org.wildfly.clustering.ee.infinispan.InfinispanConfiguration;
import org.wildfly.clustering.ejb.bean.BeanInstance;
import org.wildfly.clustering.ejb.cache.bean.BeanManagerFactoryConfiguration;
import org.wildfly.clustering.infinispan.affinity.KeyAffinityServiceFactory;
import org.wildfly.clustering.server.dispatcher.CommandDispatcherFactory;
import org.wildfly.clustering.server.group.Group;

/**
 * Encapsulates the configuration for an {@link InfinispanBeanManagerFactory}.
 * @author Paul Ferraro
 * @param <K> the bean identifier type
 * @param <V> the bean instance type
 */
public interface InfinispanBeanManagerFactoryConfiguration<K, V extends BeanInstance<K>> extends BeanManagerFactoryConfiguration<K, V>, InfinispanConfiguration {
    KeyAffinityServiceFactory getKeyAffinityServiceFactory();
    @Override Group<Address> getGroup();
    CommandDispatcherFactory getCommandDispatcherFactory();
}
