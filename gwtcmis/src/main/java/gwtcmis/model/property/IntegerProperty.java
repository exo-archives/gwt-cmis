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

package gwtcmis.model.property;

import gwtcmis.model.EnumPropertyType;

import java.util.List;

/**
 * @author <a href="mailto:andrey00x@gmail.com">Andrey Parfonov</a>
 * @version $Id: $
 */
public class IntegerProperty extends BaseProperty<Long>
{

   /**
    * Default constructor.
    */
   public IntegerProperty()
   {
      super();
   }
   
   public IntegerProperty(IntegerProperty other)
   {
      super(other);
   }

   /**
    * @param id id
    * @param queryName query name
    * @param localName local name
    * @param displayName display name
    * @param value value
    */
   public IntegerProperty(String id, String queryName, String localName, String displayName, Long value)
   {
      super(id, queryName, localName, displayName, value);
   }

   /**
    * @param id id
    * @param queryName query name
    * @param localName local name
    * @param displayName display name
    * @param values values
    */
   public IntegerProperty(String id, String queryName, String localName, String displayName, List<Long> values)
   {
      super(id, queryName, localName, displayName, values);
   }

   /**
    * {@inheritDoc}
    */
   public final EnumPropertyType getType()
   {
      return EnumPropertyType.INTEGER;
   }

}
