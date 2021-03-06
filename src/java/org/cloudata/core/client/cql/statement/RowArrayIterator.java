/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cloudata.core.client.cql.statement;

import java.io.IOException;

import org.cloudata.core.client.CTable;
import org.cloudata.core.client.Row;


/**
 * @author jindolk
 *
 */
public class RowArrayIterator implements RowIterator {
  private int currentIndex;
  
  private Row[] rows;
  
  private String[] columns;
  
  protected CTable ctable;
  
  public RowArrayIterator(CTable ctable, Row[] rows, String[] columns) {
    this.rows = rows;
    this.columns = columns;
    this.ctable = ctable;
  }

  public CTable getCTable() {
    return ctable;
  }
  
  @Override
  public boolean hasNext() throws IOException {
    if(rows == null) {
      return false;
    }
    return currentIndex < rows.length;
  }

  @Override
  public Row nextRow() throws IOException {
    if(!hasNext()) {
      return null;
    }
    return rows[currentIndex++];
  }

  @Override
  public String[] getColumns() {
    return columns;
  }

  @Override
  public void close() throws IOException {
  }
}
