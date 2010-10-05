/**
 *  Copyright (C) 2010 eXo Platform SAS.
 *
 *  This is free software; you can redistribute it and/or modify it
 *  under the terms of the GNU Lesser General Public License as
 *  published by the Free Software Foundation; either version 2.1 of
 *  the License, or (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *  Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public
 *  License along with this software; if not, write to the Free
 *  Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 *  02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

package org.gwtcmis.service.policy;

import org.gwtcmis.client.CmisArguments;
import org.gwtcmis.client.CmisMediaTypes;
import org.gwtcmis.marshallers.ApplyPolicyMarshaller;
import org.gwtcmis.marshallers.QueryMarshaller;
import org.gwtcmis.marshallers.RemovePolicyMarshaller;
import org.gwtcmis.model.EnumBaseObjectTypeIds;
import org.gwtcmis.model.EnumIncludeRelationships;
import org.gwtcmis.model.actions.ApplyPolicy;
import org.gwtcmis.model.actions.Query;
import org.gwtcmis.model.actions.RemovePolicy;
import org.gwtcmis.model.restatom.AtomEntry;
import org.gwtcmis.model.restatom.EntryCollection;
import org.gwtcmis.rest.AsyncRequest;
import org.gwtcmis.rest.AsyncRequestCallback;
import org.gwtcmis.rest.ExceptionThrownEvent;
import org.gwtcmis.rest.HTTPHeader;
import org.gwtcmis.rest.HTTPMethod;
import org.gwtcmis.service.policy.event.AllPoliciesReceivedEvent;
import org.gwtcmis.service.policy.event.AppliedPoliciesReceivedEvent;
import org.gwtcmis.service.policy.event.PolicyAppliedEvent;
import org.gwtcmis.service.policy.event.PolicyRemovedEvent;
import org.gwtcmis.unmarshallers.EntryCollectionUnmarshaller;
import org.gwtcmis.unmarshallers.EntryUnmarshaller;







import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;

/**
 * Created by The eXo Platform SAS.
 *	
 * @author <a href="mailto:zhulevaanna@gmail.com">Ann Zhuleva</a>
 * @version $Id:   ${date} ${time}
 *
 */
public class PolicyService
{
   /**
    * Event bus.
    */
   private HandlerManager eventBus;

   /**
    * @param eventBus eventBus
    */
   public PolicyService(HandlerManager eventBus)
   {
      this.eventBus = eventBus;
   }

   /**
    * Applies a specified policy to an object.
    * 
    * On success response received, {@link PolicyAppliedEvent} event is fired
    * 
    * @param url url
    * @param applyPolicy apply policy
    */
   public void applyPolicy(String url, ApplyPolicy applyPolicy)
   {
      AtomEntry policy = new AtomEntry();
      PolicyAppliedEvent event = new PolicyAppliedEvent(policy);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Policy was not applied.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(policy);
      ApplyPolicyMarshaller marshaller = new ApplyPolicyMarshaller(applyPolicy);

      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(
         marshaller).send(callback);
   }

   /**
    * Removes a specified policy from an object.
    * 
    * On success response received, {@link PolicyRemovedEvent} event is fired
    * 
    * @param url url
    * @param removePolicy remove policy
    */
   public void removePolicy(String url, RemovePolicy removePolicy)
   {
      PolicyRemovedEvent event = new PolicyRemovedEvent();
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Policy was not removed.");
      RemovePolicyMarshaller marshaller = new RemovePolicyMarshaller(removePolicy);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, null, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.X_HTTP_METHOD_OVERRIDE, HTTPMethod.DELETE).data(
         marshaller).send(callback);
   }

   /**
    * Gets the list of policies currently applied to the specified object.
    * 
    * On success response received, {@link AppliedPoliciesReceivedEvent} event is fired
    * 
    * @param url url
    * @param filter filter
    */
   public void getAppliedPolicies(String url, String filter)
   {
      EntryCollection policies = new EntryCollection();
      AppliedPoliciesReceivedEvent event = new AppliedPoliciesReceivedEvent(policies);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Applied policies were not recieved.");
      EntryCollectionUnmarshaller unmarshaller = new EntryCollectionUnmarshaller(policies);
      String params = "";
      params += (filter == null || filter.length() <= 0) ? "" : CmisArguments.FILTER + "=" + filter;
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url + "?" + params).send(callback);
   }

   /**
    * Get all policies from repository.
    * 
    * On success response received, {@link AllPoliciesReceivedEvent} event is fired
    * 
    * @param url url
    * @param repositoryId repository id
    * @param searchAllVersions search all versions
    * @param includeAllowableActions include allowable actions
    * @param includeRelationships include relationships
    * @param renditionFilter rendition filter
    * @param maxItems max items
    * @param skipCount skipCount
    */
   public void getAllPolicies(String url, String repositoryId, boolean searchAllVersions,
      boolean includeAllowableActions, EnumIncludeRelationships includeRelationships, String renditionFilter,
      Long maxItems, Long skipCount)
   {
      EntryCollection policies = new EntryCollection();
      EntryCollectionUnmarshaller unmarshaller = new EntryCollectionUnmarshaller(policies);
      AllPoliciesReceivedEvent event = new AllPoliciesReceivedEvent(policies);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Polices were not recieved.");
      /*Get all properties from repository using query*/
      String statement = "SELECT * FROM " + EnumBaseObjectTypeIds.CMIS_POLICY.value();
      Query query = new Query();
      query.setRepositoryId(repositoryId);
      query.setSearchAllVersions(searchAllVersions);
      query.setSkipCount(skipCount);
      query.setIncludeAllowableActions(includeAllowableActions);
      query.setMaxItems(maxItems);
      query.setRenditionFilter(renditionFilter);
      query.setIncludeRelationships(includeRelationships);
      query.setStatement(statement);
      QueryMarshaller marshaller = new QueryMarshaller(query);

      String params = "";
      params += (maxItems < 0) ? "" : CmisArguments.MAX_ITEMS + "=" + maxItems + "&";
      params += (skipCount < 0) ? "" : CmisArguments.SKIP_COUNT + "=" + skipCount + "&";
      params += CmisArguments.INCLUDE_RELATIONSHIPS + "=" + includeRelationships.value() + "&";
      params +=
         (renditionFilter == null || renditionFilter.length() <= 0) ? "" : CmisArguments.RENDITION_FILTER + "="
            + renditionFilter + "&";
      params += CmisArguments.INCLUDE_ALLOWABLE_ACTIONS + "=" + includeAllowableActions + "&";
      params += CmisArguments.ALL_VERSIONS + "=" + searchAllVersions;
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.QUERY_DOCUMENT).data(
         marshaller).send(callback);
   }

}
