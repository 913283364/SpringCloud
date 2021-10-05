package com.it.exception;

/**
 *  自定义的异常,提示不能删除的异常信息
 *
 *  必须继承父类
 *    Exception:
 *    RuntimeException:
 *
 *    RuntimeException 运行时异常,一旦程序抛出了运行异常,程序必须停止,修改源代码
 *    空指针,数组越界异常,类型转换ClassCast,IllegalArgumentException, 无效参数异常
 *
 *    Exception 编译异常,可以被处理,try catch处理,处理完后,程序继续执行
 *    显示异常信息:
 *       Exception父类中构造方法,传递字符串(异常信息)
 *
 */
public class CanNotDeleteException extends Exception{
    public CanNotDeleteException(String message){
        //把子类构造中的信息传递给父类构造
        super(message);
    }
}
