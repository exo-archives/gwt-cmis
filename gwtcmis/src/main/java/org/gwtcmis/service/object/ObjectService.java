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

package org.gwtcmis.service.object;

import org.gwtcmis.client.CmisArguments;
import org.gwtcmis.client.CmisMediaTypes;
import org.gwtcmis.marshallers.CreateDocumentFromSourceMarshaller;
import org.gwtcmis.marshallers.CreateDocumentMarshaller;
import org.gwtcmis.marshallers.CreateFolderMarshaller;
import org.gwtcmis.marshallers.CreatePolicyMarshaller;
import org.gwtcmis.marshallers.CreateRelationshipMarshaller;
import org.gwtcmis.marshallers.MoveObjectMarshaller;
import org.gwtcmis.marshallers.UpdateDocumentContentMarshaller;
import org.gwtcmis.marshallers.UpdatePropertiesMarshaller;
import org.gwtcmis.model.AllowableActions;
import org.gwtcmis.model.CmisContentStreamType;
import org.gwtcmis.model.EnumIncludeRelationships;
import org.gwtcmis.model.EnumUnfileObject;
import org.gwtcmis.model.actions.CreateDocument;
import org.gwtcmis.model.actions.CreateDocumentFromSource;
import org.gwtcmis.model.actions.CreateFolder;
import org.gwtcmis.model.actions.CreatePolicy;
import org.gwtcmis.model.actions.CreateRelationship;
import org.gwtcmis.model.actions.MoveObject;
import org.gwtcmis.model.actions.UpdateProperties;
import org.gwtcmis.model.restatom.AtomEntry;
import org.gwtcmis.rest.AsyncRequest;
import org.gwtcmis.rest.AsyncRequestCallback;
import org.gwtcmis.rest.ExceptionThrownEvent;
import org.gwtcmis.rest.HTTPHeader;
import org.gwtcmis.rest.HTTPMethod;
import org.gwtcmis.service.object.event.AllowableActionsReceivedEvent;
import org.gwtcmis.service.object.event.ContentStreamDeletedEvent;
import org.gwtcmis.service.object.event.ContentStreamReceivedEvent;
import org.gwtcmis.service.object.event.ContentStreamSetEvent;
import org.gwtcmis.service.object.event.DocumentContentUpdatedEvent;
import org.gwtcmis.service.object.event.DocumentCreatedEvent;
import org.gwtcmis.service.object.event.DocumentFromSourceCreatedEvent;
import org.gwtcmis.service.object.event.EmptyDocumentCreatedEvent;
import org.gwtcmis.service.object.event.FolderCreatedEvent;
import org.gwtcmis.service.object.event.ObjectDeletedEvent;
import org.gwtcmis.service.object.event.ObjectMovedEvent;
import org.gwtcmis.service.object.event.ObjectReceivedEvent;
import org.gwtcmis.service.object.event.PolicyCreatedEvent;
import org.gwtcmis.service.object.event.PropertiesReceivedEvent;
import org.gwtcmis.service.object.event.PropertiesUpdatedEvent;
import org.gwtcmis.service.object.event.RelationshipCreatedEvent;
import org.gwtcmis.service.object.event.TreeDeletedEvent;
import org.gwtcmis.unmarshallers.AllowableActionsUnmarshaller;
import org.gwtcmis.unmarshallers.ContentStreamUnmarshaller;
import org.gwtcmis.unmarshallers.EntryUnmarshaller;







import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.Random;

/**
 * Created by The eXo Platform SAS.
 *	
 * @author <a href="mailto:zhulevaanna@gmail.com">Ann Zhuleva</a>
 * @version $Id:   ${date} ${time}
 *
 */
public class ObjectService
{
   /**
    * Event bus.
    */
   private HandlerManager eventBus;

   /**
    * @param eventBus eventBus
    */
   public ObjectService(HandlerManager eventBus)
   {
      this.eventBus = eventBus;
   }

   /**
    * Creates a document object of the specified type (given by the cmis:objectTypeId property) 
    * in the (optionally) specified location.
    * 
    * On success response received, {@link DocumentCreatedEvent} event is fired.
    * 
    * @param url url
    * @param createDocument createDocument
    */
   public void createDocument(String url, CreateDocument createDocument)
   {
      AtomEntry document = new AtomEntry();
      DocumentCreatedEvent event = new DocumentCreatedEvent(document);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Document was not created.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(document);
      CreateDocumentMarshaller marshaller = new CreateDocumentMarshaller(createDocument);
      String params =
         (createDocument.getVersioningState() == null) ? "" : CmisArguments.VERSIONING_STATE + "="
            + createDocument.getVersioningState().value();
      url = (url.contains("?")) ? (url +"&"+params) : (url + "?" + params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }

   
   /**
    * @param url
    * @param createDocument
    * @param sourceUrl
    * @param contentType
    */
   public void createDocument(String url, CreateDocument createDocument, String sourceUrl)
   {
      AtomEntry document = new AtomEntry();
      DocumentCreatedEvent event = new DocumentCreatedEvent(document);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Document was not created.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(document);
      CreateDocumentMarshaller marshaller = new CreateDocumentMarshaller(createDocument, sourceUrl);
      String params =
         (createDocument.getVersioningState() == null) ? "" : CmisArguments.VERSIONING_STATE + "="
            + createDocument.getVersioningState().value();
      url = (url.contains("?")) ? (url +"&"+params) : (url + "?" + params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }
   
   
 
   /**
    * Updating document's content stream copying by url pointed in second parameter.
    * 
    * @param url document location
    * @param sourceUrl location of source for content stream
    */
   public void updateDocumentContent(String url, String sourceUrl)
   {
      AtomEntry document = new AtomEntry();
      DocumentContentUpdatedEvent event = new DocumentContentUpdatedEvent(document);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Document's content was not updated.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(document);
      UpdateDocumentContentMarshaller marshaller = new UpdateDocumentContentMarshaller(sourceUrl);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.X_HTTP_METHOD_OVERRIDE, HTTPMethod.PUT).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }
   
   
   /**
    * On success response received, {@link EmptyDocumentCreatedEvent} event is fired.
    * 
    * @param url url
    * @param createDocument createDocument
    */
   public void createEmptyDocument(String url, CreateDocument createDocument)
   {
      AtomEntry document = new AtomEntry();
      EmptyDocumentCreatedEvent event = new EmptyDocumentCreatedEvent(document);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Document was not created.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(document);
      CreateDocumentMarshaller marshaller = new CreateDocumentMarshaller(createDocument);
      String params =
         (createDocument.getVersioningState() == null) ? "" : CmisArguments.VERSIONING_STATE + "="
            + createDocument.getVersioningState().value();
      url = (url.contains("?")) ? (url +"&"+params) : (url + "?" + params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }

   /**
    * Creates a document object as a copy of the given source document in the (optionally) 
    * specified location.
    * 
    * * On success response received, {@link DocumentFromSourceCreatedEvent} event is fired.
    * 
    * @param url url
    * @param createDocumentFromSource createDocumentFromSource
    */
   public void createDocumentFromSource(String url, CreateDocumentFromSource createDocumentFromSource)
   {
      AtomEntry document = new AtomEntry();
      DocumentFromSourceCreatedEvent event = new DocumentFromSourceCreatedEvent(document);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Document was not created.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(document);
      CreateDocumentFromSourceMarshaller marshaller = new CreateDocumentFromSourceMarshaller(createDocumentFromSource);

      String params =
         (createDocumentFromSource.getVersioningState() == null) ? "" : CmisArguments.VERSIONING_STATE + "="
            + createDocumentFromSource.getVersioningState().value();
      url = (url.contains("?")) ? (url +"&"+params) : (url + "?" + params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }

   /**
    * Creates a folder object of the specified type in the specified location.
    * 
    * On success response received, {@link FolderCreatedEvent} event is fired.
    * 
    * @param url url
    * @param createFolder createFolder
    */
   public void createFolder(String url, CreateFolder createFolder)
   {
      AtomEntry folder = new AtomEntry();
      FolderCreatedEvent event = new FolderCreatedEvent(folder);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Folder was not created.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(folder);
      CreateFolderMarshaller marshaller = new CreateFolderMarshaller(createFolder);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }

   /**
    * Creates a relationship object of the specified type.
    * 
    * On success response received, {@link RelationshipCreatedEvent} event is fired.
    * 
    * @param url url
    * @param createRelationship createRelationship
    */
   public void createRelationship(String url, CreateRelationship createRelationship)
   {
      AtomEntry relationship = new AtomEntry();
      RelationshipCreatedEvent event = new RelationshipCreatedEvent(relationship);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Relationship was not created.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(relationship);
      CreateRelationshipMarshaller marshaller = new CreateRelationshipMarshaller(createRelationship);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }

   /**
    * Creates a policy object of the specified type
    * with pointed name and policy text.
    * 
    * On success response received, {@link PolicyCreatedEvent} event is fired.
    * 
    * @param url url
    * @param createPolicy createPolicy
    */
   public void createPolicy(String url, CreatePolicy createPolicy)
   {
      AtomEntry policy = new AtomEntry();
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(policy);
      PolicyCreatedEvent event = new PolicyCreatedEvent(policy);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Policy was not created.");
      CreatePolicyMarshaller marshaller = new CreatePolicyMarshaller(createPolicy);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }

   /**
    * Gets the specified information for the Object.
    * 
    * On success response received, 
    * {@link org.gwtcmis.service.object.event.AllowableActionsReceivedEvent 
    * AllowableActionsReceivedEvent} event is fired
    * 
    * @param url url
    */
   public void getAllowableActions(String url)
   {
      AllowableActions allowableActions = new AllowableActions();
      AllowableActionsReceivedEvent event = new AllowableActionsReceivedEvent(allowableActions);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Allowable actions were not received.");
      AllowableActionsUnmarshaller unmarshaller = new AllowableActionsUnmarshaller(allowableActions);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

   /**
    * Gets the specified information for the Object.
    * 
    * On success response received, 
    * {@link org.gwtcmis.service.object.event.ObjectReceivedEvent 
    * ObjectReceivedEvent} event is fired
    * 
    * @param url url
    * @param filter filter
    * @param includeEnumIncludeRelationships includeEnumIncludeRelationships
    * @param includePolicyIds includePolicyIds
    * @param renditionFilter renditionFilter
    * @param includeACL includeACL
    * @param includeAllowableActions includeAllowableActions
    */
   public void getObject(String url, String filter, EnumIncludeRelationships includeRelationships,
      boolean includePolicyIds, String renditionFilter, boolean includeACL, boolean includeAllowableActions)
   {
      AtomEntry entry = new AtomEntry();
      ObjectReceivedEvent event = new ObjectReceivedEvent(entry);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Object was not received.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(entry);

      String params = "";
      params += (filter == null || filter.length() <= 0) ? "" : CmisArguments.FILTER + "=" + filter + "&";
      params += CmisArguments.INCLUDE_RELATIONSHIPS + "=" + includeRelationships.value() + "&";
      params +=
         (renditionFilter == null || renditionFilter.length() <= 0) ? "" : CmisArguments.RENDITION_FILTER + "="
            + renditionFilter + "&";
      params += CmisArguments.INCLUDE_ACL + "=" + includeACL + "&";
      params += CmisArguments.INCLUDE_POLICY_IDS + "=" + includePolicyIds + "&";
      params += CmisArguments.INCLUDE_ALLOWABLE_ACTIONS + "=" + includeAllowableActions;
      url = (url.contains("?")) ? (url +"&"+params) : (url + "?" + params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

   /**
    * Gets the list of properties for an Object.
    * 
    * On success response received, {@link PropertiesReceivedEvent} event is fired.
    * 
    * @param url url
    * @param filter filter
    */
   public void getProperties(String url, String filter)
   {
      AtomEntry entry = new AtomEntry();
      PropertiesReceivedEvent event = new PropertiesReceivedEvent(entry);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Object properties were not recieved.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(entry);
      String params = (filter == null || filter.length() < 0) ? "" : CmisArguments.FILTER + "=" + filter;
      url = (url.contains("?")) ? (url +"&"+params) : (url + "?" + params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url).send(callback);
   }

   /**
    * Gets the content stream for the specified Document object, 
    * or gets a rendition stream for a specified rendition of a document or folder object.
    * 
    * On success response received, {@link ContentStreamReceivedEvent}  event is fired.
    * 
    * @param url url
    * @param streamId streamId
    */
   public void getContentStream(String url, String streamId)
   {
      CmisContentStreamType contentStream = new CmisContentStreamType();
      ContentStreamReceivedEvent event = new ContentStreamReceivedEvent(contentStream);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Content stream is not recieved.");
      ContentStreamUnmarshaller unmarshaller = new ContentStreamUnmarshaller(contentStream);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.GET, url + "?nocache=" + String.valueOf(Random.nextDouble())).send(callback);
   }

   /**
    * Updates properties of the specified object.
    * 
    * On success response received, {@link PropertiesUpdatedEvent} event is fired.
    * 
    * @param url url
    * @param updateProperties updateProperties
    */
   public void updateProperties(String url, UpdateProperties updateProperties)
   {
      AtomEntry entry = new AtomEntry();
      PropertiesUpdatedEvent event = new PropertiesUpdatedEvent(entry);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Object properties were not updated.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(entry);
      UpdatePropertiesMarshaller marshaller = new UpdatePropertiesMarshaller(updateProperties);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.X_HTTP_METHOD_OVERRIDE, HTTPMethod.PUT).header(
         HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }

   /**
    * Moves the specified file-able object from one folder to another.
    * 
    * On success response received, {@link ObjectMovedEvent} event is fired.
    * 
    * @param url url
    * @param moveObject moveObject
    */
   public void moveObject(String url, MoveObject moveObject)
   {
      AtomEntry entry = new AtomEntry();
      ObjectMovedEvent event = new ObjectMovedEvent(entry);
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Object was not moved.");
      EntryUnmarshaller unmarshaller = new EntryUnmarshaller(entry);
      MoveObjectMarshaller marshaller = new MoveObjectMarshaller(moveObject);
      String param = (moveObject.getSourceFolderId() == null)? "" : CmisArguments.SOURCE_FOLDER_ID + "="+moveObject.getSourceFolderId();
      url = (url.contains("?")) ? (url +"&"+param) : (url + "?" + param);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, unmarshaller, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.CONTENT_TYPE, CmisMediaTypes.ATOM_ENTRY).data(marshaller).send(callback);
   }

   /**
    * Deletes the specified object.
    * 
    * On success response received, {@link ObjectDeletedEvent} event is fired.
    * 
    * @param url url
    * @param deleteAllVersions deleteAllVersions
    */
   public void deleteObject(String url, boolean allVersions)
   {
      ObjectDeletedEvent event = new ObjectDeletedEvent();
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Object was not deleted.");
      String params = CmisArguments.ALL_VERSIONS + "=" + allVersions;
      url = (url.contains("?")) ? (url +"&"+params) : (url + "?" + params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.X_HTTP_METHOD_OVERRIDE,
         HTTPMethod.DELETE).send(callback);
   }

   /**
    * Delete tree of objects in specified folder
    * 
    * On success results received {@link TreeDeletedEvent} event is fired
    * 
    * @param url url
    * @param allVersions all versions
    * @param unfileObject unfile object
    * @param continueOnFailure continue on failure
    */
   public void deleteTree(String url, boolean allVersions, EnumUnfileObject unfileObject, boolean continueOnFailure)
   {
      TreeDeletedEvent event = new TreeDeletedEvent();
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Deletion failed.");
      String params = CmisArguments.ALL_VERSIONS + "=" + allVersions + "&";
      params += (unfileObject == null) ? "" : CmisArguments.UNFILE_OBJECTS + "=" + unfileObject.value() + "&";
      params += CmisArguments.CONTINUE_ON_FAILURE + "=" + continueOnFailure;
      url = (url.contains("?")) ? (url +"&"+params) : (url + "?" + params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.X_HTTP_METHOD_OVERRIDE,
         HTTPMethod.DELETE).send(callback);
   }

   /**
    * Sets the content stream for the specified Document object.
    * 
    * On success response received, {@link ContentStreamSetEvent} event is fired.
    * 
    * @param url
    * @param contentStream content stream
    * @param overwriteFlag overwrite flag
    * @param changeToken change token
    */
   public void setContentStream(String url, CmisContentStreamType contentStream, boolean overwriteFlag,
      String changeToken)
   {
      ContentStreamSetEvent event = new ContentStreamSetEvent();
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Content stream was not set.");
      String params = CmisArguments.OVERWRITE_FLAG + "=" + overwriteFlag + "&";
      url = (url.contains("?")) ? (url +"&"+params) : (url + "?" + params);
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.X_HTTP_METHOD_OVERRIDE,
         HTTPMethod.PUT).header(HTTPHeader.CONTENTTYPE, contentStream.getMimeType()).data(contentStream.getStream())
         .send(callback);
   }

   /**
    * Deletes the content stream for the specified Document object.
    * 
    * On success result received {@link ContentStreamDeletedEvent} is fired
    * 
    * @param url url
    * @param changeToken change token
    */
   public void deleteContentStream(String url, String changeToken)
   {
      ContentStreamDeletedEvent event = new ContentStreamDeletedEvent();
      ExceptionThrownEvent errorEvent = new ExceptionThrownEvent("Content stream was not deleted.");
      AsyncRequestCallback callback = new AsyncRequestCallback(eventBus, event, errorEvent);
      AsyncRequest.build(RequestBuilder.POST, url).header(HTTPHeader.X_HTTP_METHOD_OVERRIDE, HTTPMethod.DELETE).send(
         callback);
   }

}
