/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.shenyu.admin.model.event.plugin;

import org.apache.commons.lang3.StringUtils;
import org.apache.shenyu.admin.model.enums.EventTypeEnum;
import org.apache.shenyu.admin.model.event.AdminDataModelChangedEvent;
import org.apache.shenyu.admin.model.vo.NamespacePluginVO;
import org.apache.shenyu.common.constant.Constants;

import java.util.Objects;

/**
 * AdminDataModelChangedEvent.
 */
public class NamespacePluginChangedEvent extends AdminDataModelChangedEvent {


    /**
     * Create a new {@code PluginChangedEvent}.operator is unknown.
     *
     * @param source   Current plugin state
     * @param before   Before the change plugiin state
     * @param type     event type
     * @param operator operator
     */
    public NamespacePluginChangedEvent(final NamespacePluginVO source, final NamespacePluginVO before, final EventTypeEnum type, final String operator) {
        super(source, before, type, operator);
    }
    
    @Override
    public String buildContext() {
        final NamespacePluginVO after = (NamespacePluginVO) getAfter();
        if (Objects.isNull(getBefore())) {
            return String.format("the namespace [%s] plugin [%s] is %s", after.getNamespaceId(), after.getName(), StringUtils.lowerCase(getType().getType().toString()));
        }
        return String.format("the namespace [%s] plugin [%s] is %s : %s", after.getNamespaceId(), after.getName(), StringUtils.lowerCase(getType().getType().toString()), contrast());
        
    }
    
    private String contrast() {
        final NamespacePluginVO before = (NamespacePluginVO) getBefore();
        Objects.requireNonNull(before);
        final NamespacePluginVO after = (NamespacePluginVO) getAfter();
        Objects.requireNonNull(after);
        if (Objects.equals(before, after)) {
            return "it no change";
        }
        final StringBuilder builder = new StringBuilder();
        if (!Objects.equals(before.getConfig(), after.getConfig())) {
            builder.append(String.format("config[%s => %s] ", before.getConfig(), after.getConfig()));
        }
        if (!Objects.equals(before.getEnabled(), after.getEnabled())) {
            builder.append(String.format("enable[%s => %s] ", before.getEnabled(), after.getEnabled()));
        }
        if (!Objects.equals(before.getSort(), after.getSort())) {
            builder.append(String.format("sort[%s => %s] ", before.getSort(), after.getSort()));
        }
        return builder.toString();
    }
    
    @Override
    public String eventName() {
        return Constants.EVENT_NAME_NAMESPACE_PLUGIN;
    }
}
