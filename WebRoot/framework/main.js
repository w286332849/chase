// zTree基本配置
var setting = {
	data: {
		simpleData: {
			enable: true  // 使用简单数据类型
		}
	},
	callback: {
		onClick: zTreeOnClick  // 点击菜单之后的回调函数
	}
};
	
// 初始化
$(function(){
	$.ajax({
		url: $.path+"/menu/menu!findMenuList.action",
		type: "post",
		dataType: "json",
		success:function(response){
			
			// 加载树
			$.fn.zTree.init($("#menuTree"), setting, response.menuList);
			
			// 登陆成功提示
			$.messager.show({  
	            title:'提示',  
	            msg:'登陆成功',  
	            timeout:2000,  
	            showType:'slide'  
	        });
		},
		error:function(response){
			alert("ajax 异常");
		}
	});
})
  	
// zTree控件点击之后的回调函数
function zTreeOnClick(event, treeId, treeNode) {

	// 把相关数据加入到Tab页中
	addTab(treeNode.name,$.path + treeNode.action);
}
  	
// 加入Tab页
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
    
// Tab页使用内嵌iframe形式
function createFrame(url) {
	
	// 组装iframe
	var iFrame = '<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
	return iFrame;
}