/* 
**
** Copyright 2014, Jules White
**
** 
*/
package org.aliensource.symptommanagement.client.service;

public interface TaskCallback<T> {

    public void success(T result);

    public void error(Exception e);

}
