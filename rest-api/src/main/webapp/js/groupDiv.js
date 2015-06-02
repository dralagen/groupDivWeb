var GDtot = 56;
var GDUser = 10;
var GDAllUsers = [0, 0,0,0,0,0,0,0,0,0,0,0,0,0,0];
var echelle = 30;
var canvas = document.getElementById("mon_canvas");
var context = canvas.getContext("2d");

var xWing = new Image();
xWing.src = 'img/xwing.gif';

var edlm = new Image();
edlm.src = 'img/edlm.gif';

var yoda = new Image();
yoda.src = 'img/yoda.gif';

var xWingUser = new Image();
xWingUser.src = 'img/xwingUSER.gif';

putPicturesOnCanvas();

function eraseCanvas(){
	context.clearRect(0, 0, canvas.width, canvas.height);
}

function putPicturesOnCanvas(){
	if(GDtot === 0){
		context.drawImage(yoda, canvas.width/2 - yoda.width/2, canvas.height - yoda.height);
	}
	else{
		context.drawImage(edlm, canvas.width/2 - edlm.width/2, canvas.height - edlm.height);	
	}
	var divMinHeightPosition = canvas.height - edlm.height - xWingUser.height
	
	var j = 5;
	var k = 0;
	
	context.drawImage(xWingUser, 15 + k + j, divMinHeightPosition * GDUser / echelle);	

	for(x of GDAllUsers){
		if( k === 0){
			j += 5;
			if(j === 10){
				j = -5;
			}
		}
		context.drawImage(xWing, 15 + k + j, divMinHeightPosition - (divMinHeightPosition * x / echelle));	
		k = (k+40)%160;
	}
}

function refreshCanvas(){
	eraseCanvas();
	//TODO get values of group divergences.
	putPicturesOnCanvas();
}