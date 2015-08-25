package com.zlhw.client.base.util
{
	import flash.utils.ByteArray;
	import flash.utils.describeType;
	import flash.utils.getQualifiedClassName;
	
	import mx.collections.ArrayCollection;
	import mx.controls.Alert;

	public class ObjectUtil
	{
		
		public function ObjectUtil()
		{
		}
		/**
		 * 动态获取对象中的属性名
		 * */
		public static function getFields(obj : Object):Array{
			var instanceInfo:XML = describeType(obj); 
			var fields : Array = new Array();
			var properties:XMLList = instanceInfo..accessor.(@access != "writeonly") + instanceInfo..variable;
			for each (var propertyInfo:XML in properties){   
				var propertyName:String = propertyInfo.@name;
				fields.push(propertyName);
			} 
			return fields;
		}
		/**
		 * 动态获取object中的属性名
		 * */
		public static function getKeys(obj : Object):Array{
			var keys : Array = new Array();
			for (var key in obj) {
				if(key!="mx_internal_uid")
					keys.push(key);
			}
			return keys;
		}
		/**
		 * 动态获取对象中指定字段的注解Map(Object)
		 * */
		public static function getAnnotation(obj : Object,annotationName : String, fieldName :String):Object{
			var instanceInfo:XML = describeType(obj); 
			var annotations : Object = new Object();
			var propertyInfo:XMLList = instanceInfo..accessor.(@access != "writeonly") + instanceInfo.variable.(@name==fieldName);
			var metaArgs:XMLList = propertyInfo..metadata.(@name==annotationName)..arg;
			for each (var metaArg:XML in metaArgs){   
				annotations[String(metaArg.@key)] = String(metaArg.@value);
			}
			
			return annotations;
		}
		/**
		 * 动态获取对象中所有字段的注解Map(Object)
		 * obj[字段名][注解名][参数名]
		 * */
		public static function getAnnotations(obj : Object):Object{
			//获取对象xml描述文档
			var instanceInfo:XML = describeType(obj); 
			//最终返回的map
			var obj : Object = new Object;
			//属性列表
			var properties:XMLList = instanceInfo..accessor.(@access != "writeonly") + instanceInfo.variable;
			for each (var propertyInfo:XML in properties){   
				var annotationList:XMLList = propertyInfo.metadata;
				var annotationMap : Object = new Object;
				for each (var annotation:XML in annotationList){   
					var argList:XMLList = annotation.arg;
					var argMap : Object = new Object;
					for each (var arg:XML in argList){  
						argMap[String(arg.@key)] = String(arg.@value);
					}
					//key = 注解名称，value=该注解下参数集合
					annotationMap[String(annotation.@name)] = argMap;
				}
				//key = 属性名称，value=该属性下注解集合
				obj[String(propertyInfo.@name)] = annotationMap;
			} 
			return obj;
		}
		
		public static function getClassName(obj : Object):String
		{
			var name:String = getQualifiedClassName(obj);
			var index:int = name.indexOf("::");
			if (index != -1)
				name = name.substr(index + 2);
			
			return name;
		}
		/**
		 * 创建一个新的对象
		 * */
		public static function copy(value:Object):Object
		{
			var buffer:ByteArray = new ByteArray();
			buffer.writeObject(value);
			buffer.position = 0;
			var result:Object = buffer.readObject();
			return result;
		}
		/**
		 * 将对象中的值复制到目标对象中
		 * 
		 * */
		public static function objectCopy(obj:Object,destination:Object):void
		{
			try{
				var keys : Array = getKeys(obj);
				if(keys.length < 1){
					keys = getFields(obj);
				}

				for each(var key : String in keys){
					destination[key] = obj[key];
				}
			}catch(e:Error){
				trace(e.getStackTrace());
			}
			
		}
		public static function objToCollection(obj:Object):ArrayCollection{
			var collection : ArrayCollection = new ArrayCollection();
			for (var key in obj) {
				var tmpObj : Object = new Object;
				tmpObj.key = key;
				tmpObj.value = obj[key];
				collection.addItem(tmpObj);
			}
			return collection;
		}
	}
}