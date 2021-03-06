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
package org.cloudata.core.common;

import java.io.File;
import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

public class StaticTestEnvironment {
  private StaticTestEnvironment() {};                   // Not instantiable

  public static final String TEST_DIRECTORY_KEY = "test.build.data";
  public static boolean debugging = true;

  @SuppressWarnings("unchecked")
  public static void initialize() {
    String value = null;
    
    if (System.getProperty(TEST_DIRECTORY_KEY) == null) {
      System.setProperty(TEST_DIRECTORY_KEY, new File("build/test").getAbsolutePath());
    }
    
    value = System.getenv("DEBUGGING");
    if(value != null && value.equalsIgnoreCase("TRUE")) {
      debugging = true;
      
      Logger rootLogger = Logger.getRootLogger();
      rootLogger.setLevel(Level.WARN);

      Level logLevel = Level.INFO;
      value = System.getenv("LOGGING_LEVEL");
      if(value != null && value.length() != 0) {
        if(value.equalsIgnoreCase("ALL")) {
          logLevel = Level.ALL;
        } else if(value.equalsIgnoreCase("DEBUG")) {
          logLevel = Level.DEBUG;
        } else if(value.equalsIgnoreCase("ERROR")) {
          logLevel = Level.ERROR;
        } else if(value.equalsIgnoreCase("FATAL")) {
          logLevel = Level.FATAL;
        } else if(value.equalsIgnoreCase("INFO")) {
          logLevel = Level.INFO;
        } else if(value.equalsIgnoreCase("OFF")) {
          logLevel = Level.OFF;
        } else if(value.equalsIgnoreCase("TRACE")) {
          logLevel = Level.TRACE;
        } else if(value.equalsIgnoreCase("WARN")) {
          logLevel = Level.WARN;
        }
      }
      ConsoleAppender consoleAppender = null;
      for(Enumeration<Appender> e = rootLogger.getAllAppenders();
          e.hasMoreElements();) {

        Appender a = e.nextElement();
        if(a instanceof ConsoleAppender) {
          consoleAppender = (ConsoleAppender)a;
          break;
        }
      }
      if(consoleAppender != null) {
        Layout layout = consoleAppender.getLayout();
        if(layout instanceof PatternLayout) {
          PatternLayout consoleLayout = (PatternLayout)layout;
          consoleLayout.setConversionPattern("%d %-5p [%t] %l: %m%n");
        }
      }
      Logger.getLogger(CloudataTestCase.class.getPackage().getName()).setLevel(logLevel);
    }    
  }
}
