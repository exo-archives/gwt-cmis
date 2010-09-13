/*
 * Copyright (C) 2010 eXo Platform SAS.
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
package gwtcmis.object.impl;

import gwtcmis.object.CmisDocument;
import gwtcmis.object.CmisObject;

import java.util.HashSet;


/**
 * Created by The eXo Platform SAS.
 *	
 * @author <a href="mailto:zhulevaanna@gmail.com">Ann Zhuleva</a>
 * @version $Id:   ${date} ${time}
 *
 */
public class CmisDocumentImpl extends CmisObjectImpl implements CmisDocument
{

   /**
    * Is latest version.
    */
   private Boolean latestVersion;

   /**
    * Is major version.
    */
   private Boolean majorVersion;

   /**
    * Is latest major version.
    */
   private Boolean latestMajorVersion;

   /**
    * Is version series checked out.
    */
   private Boolean versionSeriesCheckedOut;

   /**
    * Version series id.
    */
   private String versionSeriesId;

   /**
    * Version series checked out id
    */
   private String versionSeriesCheckedOutId;

   /**
    * Version series checked out by
    */
   private String versionSeriesCheckedOutBy;

   /**
    * Version label
    */
   private String versionLabel;

   /**
    * Conent stream mime type
    */
   private String contentStreamMimeType;

   /**
    * Conent stream length
    */
   private Long contentStreamLenght;
   
   
   public CmisDocumentImpl(CmisObject object)
   {
      super(object.getProperties().getProperties(), object.getACL(), object.isExactACL(), 
         new HashSet<String>(object.getPolicyIds()), object.getRelationship(), object.getRenditions(), 
         object.getAllowableActions(), object.getChangeInfo(), object.getObjectInfo(), 
         object.getPathSegment());
   }

   
   
   /**
    * @see gwtcmis.object.CmisDocument#getContentStreamLenght()
    */
   public Long getContentStreamLenght()
   {
      return contentStreamLenght;
   }

   /**
    * @see gwtcmis.object.CmisDocument#getContentStreamMimeType()
    */
   public String getContentStreamMimeType()
   {
      return contentStreamMimeType;
   }

   /**
    * @see gwtcmis.object.CmisDocument#getLatestMajorVersion()
    */
   public Boolean getLatestMajorVersion()
   {
      return latestMajorVersion;
   }

   /**
    * @see gwtcmis.object.CmisDocument#getLatestVersion()
    */
   public Boolean getLatestVersion()
   {
      return latestVersion;
   }

   /**
    * @see gwtcmis.object.CmisDocument#getMajorVersion()
    */
   public Boolean getMajorVersion()
   {
      return majorVersion;
   }

   /**
    * @see gwtcmis.object.CmisDocument#getVersionLabel()
    */
   public String getVersionLabel()
   {
      return versionLabel;
   }

   /**
    * @see gwtcmis.object.CmisDocument#getVersionSeriesCheckedOut()
    */
   public Boolean getVersionSeriesCheckedOut()
   {
      return versionSeriesCheckedOut;
   }

   /**
    * @see gwtcmis.object.CmisDocument#getVersionSeriesCheckedOutBy()
    */
   public String getVersionSeriesCheckedOutBy()
   {
      return versionSeriesCheckedOutBy;
   }

   /**
    * @see gwtcmis.object.CmisDocument#getVersionSeriesCheckedOutId()
    */
   public String getVersionSeriesCheckedOutId()
   {
      return versionSeriesCheckedOutId;
   }

   /**
    * @see gwtcmis.object.CmisDocument#getVersionSeriesId()
    */
   public String getVersionSeriesId()
   {
      return versionSeriesId;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setContentStreamLenght(java.lang.Long)
    */
   public void setContentStreamLenght(Long contentStreamLenght)
   {
      this.contentStreamLenght = contentStreamLenght;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setContentStreamMimeType(java.lang.String)
    */
   public void setContentStreamMimeType(String contentStreamMimeType)
   {
      this.contentStreamMimeType = contentStreamMimeType;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setLatestMajorVersion(java.lang.Boolean)
    */
   public void setLatestMajorVersion(Boolean latestMajorVersion)
   {
      this.latestMajorVersion = latestMajorVersion;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setLatestVersion(java.lang.Boolean)
    */
   public void setLatestVersion(Boolean latestVersion)
   {
      this.latestVersion = latestVersion;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setMajorVersion(java.lang.Boolean)
    */
   public void setMajorVersion(Boolean majorVersion)
   {
      this.majorVersion = majorVersion;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setVersionLabel(java.lang.String)
    */
   public void setVersionLabel(String versionLabel)
   {
      this.versionLabel = versionLabel;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setVersionSeriesCheckedOut(java.lang.Boolean)
    */
   public void setVersionSeriesCheckedOut(Boolean versionSeriesCheckedOut)
   {
      this.versionSeriesCheckedOut = versionSeriesCheckedOut;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setVersionSeriesCheckedOutBy(java.lang.String)
    */
   public void setVersionSeriesCheckedOutBy(String versionSeriesCheckedOutBy)
   {
      this.versionSeriesCheckedOutBy = versionSeriesCheckedOutBy;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setVersionSeriesCheckedOutId(java.lang.String)
    */
   public void setVersionSeriesCheckedOutId(String versionSeriesCheckedOutId)
   {
      this.versionSeriesCheckedOutId = versionSeriesCheckedOutId;
   }

   /**
    * @see gwtcmis.object.CmisDocument#setVersionSeriesId(java.lang.String)
    */
   public void setVersionSeriesId(String versionSeriesId)
   {
      this.versionSeriesId = versionSeriesId;
   }

}
