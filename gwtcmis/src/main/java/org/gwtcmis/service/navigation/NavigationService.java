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

package org.gwtcmis.service.navigation;

import org.gwtcmis.client.CmisArguments;
import org.gwtcmis.model.EnumIncludeRelationships;
import org.gwtcmis.model.restatom.AtomEntry;
import org.gwtcmis.model.restatom.EntryCollection;
import org.gwtcmis.rest.AsyncRequest;
import org.gwtcmis.rest.AsyncRequestCallback;
import org.gwtcmis.rest.ExceptionThrownEvent;
import org.gwtcmis.service.navigation.event.CheckedOutReceivedEvent;
import org.gwtcmis.service.navigation.event.ChildrenReceivedEvent;
import org.gwtcmis.service.navigation.event.DescendantsReceivedEvent;
import org.gwtcmis.service.navigation.event.FolderParentReceivedEvent;
import org.gwtcmis.service.navigation.event.FolderTreeReceivedEvent;
import org.gwtcmis.service.navigation.event.NextPageReceivedEvent;
import org.gwtcmis.service.navigation.event.ObjectParentsReceivedEvent;
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
public class NavigationService
{

   /**
    * Event bus.
    */
   private HandlerManager eventBus;

   /**
    * @param eventBus eventBus
    */
   public NavigationService(HandlerManager eventBus)
   {
      this.eventBus = eventBus;
   }

   /**
    * Gets the list of child objects contained in the specified folder.
    * 
    * On success response received, 
    * {@link org.gwtcmis.service.navigation.event.ChildrenReceivedEvent 
    * ChildrenReceivedEvent} event is fired 
    * 
    * @param url url
    * @param maxItems maxItems
    * @param skipCount skipCount
    * @param filter filter
    * @param includeRelationships includeRelationships
    * @param renditionFilter renditionFilter
    * @param includeAllowableActions includeAllowableActions
    * @param includePathSegment includePathSegment
    */
   public void getChildren(String url, int maxItems, int skipCount, String filter,
      EnumIncludeRelationships includeRelationships, String renditionFilter, boolean includeAllowableActions,
      boolean includePathSegment)
   {
      EntryCollection entryCollection = new EntryCollection();
      ChildrenReceivedEvent event = new ChildrenReceivedEvent(entryCollection);
      EntryCollectionUnmarshaller unmarshaller = new EntryCollectionUnmarshaller(entryCollection);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Folder content was not received.");
      
      String params = "";
      params += (maxItems < 0) ? "" : CmisArguments.MAX_ITEMS + "=" + maxItems + "&";
      params += (skipCount < 0) ? "" : CmisArguments.SKIP_COUNT + "=" + skipCount + "&";
      params += (filter == null || filter.length() <= 0) ? "" : CmisArguments.FILTER + "=" + filter + "&";
      params += CmisArguments.INCLUDE_RELATIONSHIPS + "=" + includeRelationships.value() + "&";
      params +=
         (renditionFilter == null || renditionFilter.length() <= 0) ? "" : CmisArguments.RENDITION_FILTER + "="
            + renditionFilter + "&";
      params += CmisArguments.INCLUDE_ALLOWABLE_ACTIONS + "=" + includeAllowableActions + "&";
      params += CmisArguments.INCLUDE_PATH_SEGMENT + "=" + includePathSegment;
      url = url.contains("?") ? (url +"&"+params) : (url +"?"+params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

   /**
    * To support paging and get next page with items.
    * 
    * On success response received, NextPageReceivedEvent event is fired.
    * 
    * @param url url
    * @param filter filter
    * @param includeRelationships includeRelationships
    * @param renditionFilter renditionFilter
    * @param includeAllowableActions includeAllowableActions
    * @param includePathSegment includePathSegment
    */
   public void getNextPage(String url, String filter,
      EnumIncludeRelationships includeRelationships, String renditionFilter, boolean includeAllowableActions,
      boolean includePathSegment)
   {
      EntryCollection entryCollection = new EntryCollection();
      NextPageReceivedEvent event = new NextPageReceivedEvent(entryCollection);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Folder content was not received.");
      EntryCollectionUnmarshaller unmarshaller = new EntryCollectionUnmarshaller(entryCollection);
      
      String params = "";
      params += (filter == null || filter.length() <= 0) ? "" : CmisArguments.FILTER + "=" + filter + "&";
      params += CmisArguments.INCLUDE_RELATIONSHIPS + "=" + includeRelationships.value() + "&";
      params +=
         (renditionFilter == null || renditionFilter.length() <= 0) ? "" : CmisArguments.RENDITION_FILTER + "="
            + renditionFilter + "&";
      params += CmisArguments.INCLUDE_ALLOWABLE_ACTIONS + "=" + includeAllowableActions + "&";
      params += CmisArguments.INCLUDE_PATH_SEGMENT + "=" + includePathSegment + "&";
      url = url.contains("?") ? (url +"&"+params) : (url +"?"+params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

   /**
    * Gets the set of descendant objects contained in the specified folder or any of its childfolders.
    * 
    * On success response received, 
    * {@link org.gwtcmis.service.navigation.event.DescendantsReceivedEvent 
    * DescendantsReceivedEvent} event is fired
    * 
    * @param url url
    * @param depth depth
    * @param filter filter
    * @param includeRelationships includeRelationships
    * @param renditionFilter renditionFilter
    * @param includeAllowableActions includeAllowableActions
    * @param includePathSegment includePathSegment
    */
   public void getDescendants(String url, int depth, String filter, EnumIncludeRelationships includeRelationships,
      String renditionFilter, boolean includeAllowableActions, boolean includePathSegment)
   {
      EntryCollection entryCollection = new EntryCollection();
      DescendantsReceivedEvent event = new DescendantsReceivedEvent(entryCollection);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Folder descendants were not received.");
      EntryCollectionUnmarshaller unmarshaller = new EntryCollectionUnmarshaller(entryCollection);

      String params = "";
      params += (depth < -1) ? "" : CmisArguments.DEPTH + "=" + depth + "&";
      params += (filter == null || filter.length() <= 0) ? "" : CmisArguments.FILTER + "=" + filter + "&";
      params += CmisArguments.INCLUDE_RELATIONSHIPS + "=" + includeRelationships.value() + "&";
      params +=
         (renditionFilter == null || renditionFilter.length() <= 0) ? "" : CmisArguments.RENDITION_FILTER + "="
            + renditionFilter + "&";
      params += CmisArguments.INCLUDE_ALLOWABLE_ACTIONS + "=" + includeAllowableActions + "&";
      params += CmisArguments.INCLUDE_PATH_SEGMENT + "=" + includePathSegment;
      url = url.contains("?") ? (url +"&"+params) : (url +"?"+params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

   /**
    * Gets the set of descendant folder objects contained in the specified folder.
    * 
    * On success response received, 
    * {@link org.gwtcmis.service.navigation.event.FolderTreeReceivedEvent 
    * FolderTreeReceivedEvent} event is fired. 
    * 
    * @param url url
    * @param depth depth
    * @param filter filter
    * @param includeRelationships includeRelationships
    * @param renditionFilter renditionFilter
    * @param includeAllowableActions includeAllowableActions
    * @param includePathSegment includePathSegment
    */
   public void getFolderTree(String url, int depth, String filter, EnumIncludeRelationships includeRelationships,
      String renditionFilter, boolean includeAllowableActions, boolean includePathSegment)
   {
      EntryCollection entryCollection = new EntryCollection();
      FolderTreeReceivedEvent event = new FolderTreeReceivedEvent(entryCollection);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Folder content was not received.");
      EntryCollectionUnmarshaller unmarshaller = new EntryCollectionUnmarshaller(entryCollection);
      
      String params = "";
      params += (depth < -1) ? "" : CmisArguments.DEPTH + "=" + depth + "&";
      params += (filter == null || filter.length() <= 0) ? "" : CmisArguments.FILTER + "=" + filter + "&";
      params += CmisArguments.INCLUDE_RELATIONSHIPS + "=" + includeRelationships.value() + "&";
      params +=
         (renditionFilter == null || renditionFilter.length() <= 0) ? "" : CmisArguments.RENDITION_FILTER + "="
            + renditionFilter + "&";
      params += CmisArguments.INCLUDE_ALLOWABLE_ACTIONS + "=" + includeAllowableActions + "&";
      params += CmisArguments.INCLUDE_PATH_SEGMENT + "=" + includePathSegment;
      url = url.contains("?") ? (url +"&"+params) : (url +"?"+params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

   /**
    * Gets the parent folder object for the specified folder object.
    * 
    * On success response received, FolderParentReceivedEvent event is fired.
    * 
    * @param url url
    * @param filter filter
    */
   public void getFolderParent(String url, String filter)
   {
      AtomEntry entry = new AtomEntry();
      FolderParentReceivedEvent event = new FolderParentReceivedEvent(entry);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Folder parent was not received.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(entry);
      String params = (filter == null || filter.length() <= 0) ? "" : CmisArguments.FILTER + "=" + filter + "&";
      url = url.contains("?") ? (url +"&"+params) : (url +"?"+params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

   /**
    * Gets the parent folder(s) for the specified non-folder, fileable object.
    * 
    * On success response received, 
    * {@link org.gwtcmis.service.navigation.event.ObjectParentsReceivedEvent 
    * ObjectParentsReceivedEvent} event is fired.
    * 
    * @param url url
    * @param filter filter
    * @param includeEnumIncludeRelationships includeEnumIncludeRelationships
    * @param renditionFilter renditionFilter
    * @param includeAllowableActions includeAllowableActions
    * @param includeRelativePathSegment includeRelativePathSegment
    */
   public void getObjectParents(String url, String filter, EnumIncludeRelationships includeRelationships,
      String renditionFilter, boolean includeAllowableActions, boolean includeRelativePathSegment)
   {
      EntryCollection entryCollection = new EntryCollection();
      ObjectParentsReceivedEvent event = new ObjectParentsReceivedEvent(entryCollection);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Object parents were not received.");
      EntryCollectionUnmarshaller unmarshaller = new EntryCollectionUnmarshaller(entryCollection);

      String params = "";
      params += CmisArguments.INCLUDE_RELATIONSHIPS + "=" + includeRelationships.value() + "&";
      params += (filter == null || filter.length() <= 0) ? "" : CmisArguments.FILTER + "=" + filter + "&";
      params +=
         (renditionFilter == null || renditionFilter.length() <= 0) ? "" : CmisArguments.RENDITION_FILTER + "="
            + renditionFilter + "&";
      params += CmisArguments.INCLUDE_ALLOWABLE_ACTIONS + "=" + includeAllowableActions + "&";
      params += CmisArguments.INCLUDE_RELATIVE_PATH_SEGMENT + "=" + includeRelativePathSegment;
      url = url.contains("?") ? (url +"&"+params) : (url +"?"+params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

   /**
    * Gets the list of documents that are checked out that the user has access to.
    * 
    * On success response received, CheckedOutReceivedEvent event is fired.
    * 
    * @param url url
    * @param folderId folderId
    * @param maxItems maxItems
    * @param skipCount skipCount
    * @param filter filter
    * @param includeRelationships includeRelationships
    * @param renditionFilter renditionFilter
    * @param includeAllowableActions includeAllowableActions
    */
   public void getCheckedOut(String url, String folderId, int maxItems, int skipCount, String filter,
      EnumIncludeRelationships includeRelationships, String renditionFilter, boolean includeAllowableActions)
   {
      EntryCollection entryCollection = new EntryCollection();
      CheckedOutReceivedEvent event = new CheckedOutReceivedEvent(entryCollection);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Checked out documents are not found.");
      EntryCollectionUnmarshaller unmarshaller = new EntryCollectionUnmarshaller(entryCollection);

      String params = "";
      params += (folderId == null || folderId.length() < 0) ? "" : CmisArguments.FOLDER_ID + "=" + folderId + "&";
      params += (maxItems < 0) ? "" : CmisArguments.MAX_ITEMS + "=" + maxItems + "&";
      params += (skipCount < 0) ? "" : CmisArguments.SKIP_COUNT + "=" + skipCount + "&";
      params += (filter == null || filter.length() <= 0) ? "" : CmisArguments.FILTER + "=" + filter + "&";
      params += CmisArguments.INCLUDE_RELATIONSHIPS + "=" + includeRelationships.value() + "&";
      params +=
         (renditionFilter == null || renditionFilter.length() <= 0) ? "" : CmisArguments.RENDITION_FILTER + "="
            + renditionFilter + "&";
      params += (skipCount < 0) ? "" : CmisArguments.SKIP_COUNT + "=" + skipCount + "&";
      params += CmisArguments.INCLUDE_ALLOWABLE_ACTIONS + "=" + includeAllowableActions;
      url = url.contains("?") ? (url +"&"+params) : (url +"?"+params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

}
