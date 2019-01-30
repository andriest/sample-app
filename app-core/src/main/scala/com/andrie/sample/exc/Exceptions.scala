package com.andrie.sample.exc

/**
  * Author: andrie (andriebamz@gmail.com)
  *
  */
class AppException(msg:String) extends Exception(msg)

case class NotExistException(msg:String) extends AppException(msg)
