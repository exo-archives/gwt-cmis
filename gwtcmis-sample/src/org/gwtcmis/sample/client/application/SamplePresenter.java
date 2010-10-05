/**
 * Copyright (C) 2003-2009 eXo Platform SAS.
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation; either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see<http://www.gnu.org/licenses/>.
 */

package org.gwtcmis.sample.client.application;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasClickHandlers;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.user.client.Window;

import org.gwtcmis.client.CMIS;
import org.gwtcmis.model.EnumBaseObjectTypeIds;
import org.gwtcmis.model.EnumIncludeRelationships;
import org.gwtcmis.model.actions.CreateDocument;
import org.gwtcmis.model.actions.CreateFolder;
import org.gwtcmis.model.property.CmisProperties;
import org.gwtcmis.model.property.IdProperty;
import org.gwtcmis.model.property.Property;
import org.gwtcmis.model.property.StringProperty;
import org.gwtcmis.model.repository.CmisRepositoryInfo;
import org.gwtcmis.model.restatom.AtomEntry;
import org.gwtcmis.model.restatom.AtomLink;
import org.gwtcmis.model.restatom.EnumCollectionType;
import org.gwtcmis.model.restatom.EnumLinkRelation;
import org.gwtcmis.model.restatom.EnumRenditionFilter;
import org.gwtcmis.rest.ExceptionThrownEvent;
import org.gwtcmis.rest.ExceptionThrownHandler;
import org.gwtcmis.rest.ServerException;
import org.gwtcmis.service.navigation.NavigationService;
import org.gwtcmis.service.navigation.event.ChildrenReceivedEvent;
import org.gwtcmis.service.navigation.event.ChildrenReceivedHandler;
import org.gwtcmis.service.object.ObjectService;
import org.gwtcmis.service.object.event.DocumentCreatedEvent;
import org.gwtcmis.service.object.event.DocumentCreatedHandler;
import org.gwtcmis.service.object.event.FolderCreatedEvent;
import org.gwtcmis.service.object.event.FolderCreatedHandler;
import org.gwtcmis.service.repository.RepositoryService;
import org.gwtcmis.service.repository.event.RepositoriesReceivedEvent;
import org.gwtcmis.service.repository.event.RepositoriesReceivedHandler;

import java.util.HashMap;
import java.util.List;

/**
 * Created by The eXo Platform SAS.
 * 
 * @author <a href="mailto:zhulevaanna@gmail.com">Anna Zhuleva</a>
 * @version $Id:
 */

public class SamplePresenter implements RepositoriesReceivedHandler, ChildrenReceivedHandler, FolderCreatedHandler,
   DocumentCreatedHandler, ExceptionThrownHandler
{
   private String url = "http://xcmis.org/xcmis1/rest/cmisatom";

   private RepositoryService repositoryService;

   private ObjectService objectService;

   private NavigationService navigationService;

   private CmisRepositoryInfo currentRepository;

   private int newFolderCount = 0;

   private int newDocumentCount = 0;

   public interface Display
   {
      void showObjects(List<AtomEntry> entries);

      void addNewObject(AtomEntry entry);

      String removeObject();

      void displayRepository(CmisRepositoryInfo repositoryInfo);

      HasClickHandlers getCreateFolderButton();

      HasClickHandlers getCreateDocumentButton();

      HasClickHandlers getDeleteButton();

      void setEnableCreateButtons(boolean enable);

      void setEnableDeleteButton(boolean enable);

   }

   private Display display;

   private HandlerManager eventBus;

   /**
    * @param eventBus
    */
   public SamplePresenter(HandlerManager eventBus)
   {
      this.eventBus = eventBus;
      repositoryService = new RepositoryService(eventBus);
      navigationService = new NavigationService(eventBus);
      objectService = new ObjectService(eventBus);
   }

   /**
    * @param d
    */
   public void bindDisplay(Display d)
   {
      display = d;
      eventBus.addHandler(RepositoriesReceivedEvent.TYPE, this);
      eventBus.addHandler(ChildrenReceivedEvent.TYPE, this);
      eventBus.addHandler(FolderCreatedEvent.TYPE, this);
      eventBus.addHandler(DocumentCreatedEvent.TYPE, this);

      eventBus.addHandler(ExceptionThrownEvent.TYPE, this);

      display.getCreateFolderButton().addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent arg0)
         {
            createFolder();
         }
      });

      display.getCreateDocumentButton().addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent arg0)
         {
            createDocument();
         }
      });

      display.getDeleteButton().addClickHandler(new ClickHandler()
      {
         public void onClick(ClickEvent event)
         {
            String url = display.removeObject();
            if (url != null)
            {
               deleteObject(url);
            }
         }
      });

      /*Get repositories provided by service pointed by url*/
      repositoryService.getRepositories(url);
   }

   /**
    * @see org.xcmis.gwtframework.client.services.repository.event.RepositoriesReceivedHandler#onRepositoriesReceived(org.xcmis.gwtframework.client.services.repository.event.RepositoriesReceivedEvent)
    */
   public void onRepositoriesReceived(RepositoriesReceivedEvent event)
   {
      List<CmisRepositoryInfo> repositories = event.getRepositories().list();
      for (CmisRepositoryInfo repository : event.getRepositories().list())
      {
         display.displayRepository(repository);
      }

      if (repositories.size() > 0)
      {
         currentRepository = repositories.get(0);
         int maxItems = 10;
         int skipCount = 0;
         EnumIncludeRelationships includeRelationships = EnumIncludeRelationships.NONE;
         String renditionFilter = EnumRenditionFilter.NONE_FILTER.value();
         boolean includeAllowableActions = true;
         boolean includePathSegment = false;
         String rootFolderUrl = currentRepository.getCollectionValue(EnumCollectionType.ROOT);
         navigationService.getChildren(rootFolderUrl, maxItems, skipCount, null, includeRelationships, renditionFilter,
            includeAllowableActions, includePathSegment);
         display.setEnableCreateButtons(true);
      }

   }

   /**
    * @see org.xcmis.gwtframework.client.services.navigation.event.ChildrenReceivedHandler#onChildrenReceived(org.xcmis.gwtframework.client.services.navigation.event.ChildrenReceivedEvent)
    */
   public void onChildrenReceived(ChildrenReceivedEvent event)
   {
      int count = event.getChildren().getEntries().size();
      if (count > 0)
      {
         display.showObjects(event.getChildren().getEntries());
      }
      else
      {
         Window.alert("No objects found.");
      }
   }

   /**
    * Create new folder object
    */
   private void createFolder()
   {
      CreateFolder createFolder = new CreateFolder();
      createFolder.setRepositoryId(currentRepository.getRepositoryId());
      createFolder.setFolderId(currentRepository.getRootFolderId());

      newFolderCount++;
      CmisProperties properties = new CmisProperties(new HashMap<String, Property<?>>());
      String propertyId = CMIS.CMIS_NAME;
      String name = "Folder " + newFolderCount;
      properties.getProperties().put(propertyId,
         new StringProperty(propertyId, propertyId, propertyId, propertyId, name));
      propertyId = CMIS.CMIS_OBJECT_TYPE_ID;
      properties.getProperties().put(propertyId,
         new IdProperty(propertyId, propertyId, propertyId, propertyId, CMIS.BASE_TYPE_FOLDER));
      createFolder.setProperties(properties);

      String rootFolderUrl = currentRepository.getCollectionValue(EnumCollectionType.ROOT);
      objectService.createFolder(rootFolderUrl, createFolder);
   }

   /**
    * Create new document object
    */
   private void createDocument()
   {
      CreateDocument createDocument = new CreateDocument();
      createDocument.setRepositoryId(currentRepository.getRepositoryId());
      createDocument.setFolderId(currentRepository.getRootFolderId());

      newDocumentCount++;

      CmisProperties properties = new CmisProperties(new HashMap<String, Property<?>>());
      String propertyId = CMIS.CMIS_NAME;
      String name = "Document " + newDocumentCount;
      properties.getProperties().put(propertyId,
         new StringProperty(propertyId, propertyId, propertyId, propertyId, name));
      propertyId = CMIS.CMIS_OBJECT_TYPE_ID;
      properties.getProperties().put(propertyId,
         new IdProperty(propertyId, propertyId, propertyId, propertyId, CMIS.BASE_TYPE_DOCUMENT));
      createDocument.setProperties(properties);

      String rootFolderUrl = currentRepository.getCollectionValue(EnumCollectionType.ROOT);

      objectService.createDocument(rootFolderUrl, createDocument);
   }

   /**
    * @see org.xcmis.gwtframework.client.services.object.event.FolderCreatedHandler#onFolderCreated(org.xcmis.gwtframework.client.services.object.event.FolderCreatedEvent)
    */
   public void onFolderCreated(FolderCreatedEvent event)
   {
      display.addNewObject(event.getFolder());
   }

   /**
    * @see org.xcmis.gwtframework.client.services.object.event.DocumentCreatedHandler#onDocumentCreated(org.xcmis.gwtframework.client.services.object.event.DocumentCreatedEvent)
    */
   public void onDocumentCreated(DocumentCreatedEvent event)
   {
      display.addNewObject(event.getDocument());
   }

   /**
    * Delete object
    * 
    * @param url
    */
   public void deleteObject(String url)
   {
      objectService.deleteObject(url, true);
   }

   /**
    * Get url, by which object will be deleted
    * 
    * @param baseObjectTypeIds
    * @param links
    * @return String
    */
   public String getUrlForDelete(EnumBaseObjectTypeIds baseObjectTypeIds, List<AtomLink> links)
   {
      EnumLinkRelation relation = null;

      if (baseObjectTypeIds.equals(EnumBaseObjectTypeIds.CMIS_FOLDER))
      {
         relation = EnumLinkRelation.DOWN;
      }
      else if (baseObjectTypeIds.equals(EnumBaseObjectTypeIds.CMIS_DOCUMENT))
      {
         /* If object is document get self */
         relation = EnumLinkRelation.SELF;
      }
      /* Get link with pointed relation*/
      for (AtomLink link : links)
      {
         if (link.getRelation().equals(relation))
         {
            return link.getHref();
         }
      }
      return null;
   }

   /**
    * @see org.xcmis.client.gwt.rest.ExceptionThrownHandler#onError(org.xcmis.client.gwt.rest.ExceptionThrownEvent)
    */
   public void onError(ExceptionThrownEvent event)
   {
      Throwable error = event.getError();
      if (error instanceof ServerException)
      {
         ServerException serverException = (ServerException)error;

         if (serverException.isErrorMessageProvided())
         {
            Window.alert(serverException.getMessage());
         }
         else if (event.getErrorMessage() != null)
         {
            Window.alert(event.getErrorMessage());
         }
         else
         {
            String errorText = "" + serverException.getHTTPStatus() + "&nbsp;" + serverException.getStatusText();
            Window.alert(errorText);
         }
      }
      else
      {
         Window.alert(error.getMessage());
         error.printStackTrace();
      }
   }

}
