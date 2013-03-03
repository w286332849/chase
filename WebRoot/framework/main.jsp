<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/common/import.jsp" %>
<!DOCTYPE html>
<html>
<head>
</head>
<body>
   <div class="easyui-layout" style="width:100%;height:640px;">
   
        <div data-options="region:'north'" style="height:120px">
        	
        </div>  
        
        <div data-options="region:'west',split:true" title="菜单" style="width:180px;">  
            <ul class="easyui-tree" data-options="url:'../layout/tree_data1.json',animate:true,dnd:true">
            	<li>  
            		<span>系统管理</span>
            		<ul>
		            	 <li>  
		                     <a href="#none" onclick="addTab('用户管理','${path}/framework/user/userList.jsp')">用户管理</a>  
		                 </li>  
		             </ul>
		          </li>
            </ul>
        </div>  
        
        <div data-options="region:'center',title:'欢迎 ${user.username}'">  
        	<div id="mainTab" class="easyui-tabs" fit="true"> 
	             <div id="tabs" title="About" style="padding:5px">
	             	<P>欢迎使用Chase框架</P>
	             </div> 
            </div> 
        </div> 
    </div>
</body>  
  <script type="text/javascript">
  	$(function(){
  		$.messager.show({  
            title:'提示',  
            msg:'登陆成功',  
            timeout:2000,  
            showType:'slide'  
        });
  	})
  	
  
    function addTab(name,url){
        if ($('#mainTab').tabs('exists',name)) {
            $('#mainTab').tabs('select', name);
        } else {
            $('#mainTab').tabs('add',{
                title:name,
                closable:true,
                content:createFrame(url)
            });
        }
    }
    
    function createFrame(url) {
		var iFrame = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
		return iFrame;
	}
	        	
  </script>
</html>
