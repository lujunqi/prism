/**
* 主工作区的侧边缩放
*/
var displayBar = true;
function switchBar() {
	var $parentDoc = $(window.parent.document);
	var $togBtn = $('#togSide');
    if (displayBar) {
        displayBar=false;
		$('#mid',$parentDoc).attr('cols', '0,*');
		$togBtn.text('>').attr('title', '打开左边管理菜单');
    }
    else {
        displayBar=true;
		$('#mid',$parentDoc).attr('cols', '190,*');
		$togBtn.text('<').attr('title', '打开左边管理菜单');
    }
}
$(document).ready(function() {
	$('body.mainBody').append('<a id="togSide" href="#" title="收缩侧边栏">&lt;</a>');
	$('#togSide').click(switchBar);
});	