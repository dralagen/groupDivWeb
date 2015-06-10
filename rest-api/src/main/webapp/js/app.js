
	var app = angular.module("groupDiv", ['groupDiv.choicesController']);
	
	app.controller("ExpController", function(){
		this.tab = 1;
		
		this.selectTab = function(setTab){
			this.tab = setTab;
		};
		
		this.isSelected = function(checkTab){
			return this.tab === checkTab;
		};
		
		this.GDtot = 56;
		this.GDUser = 10;
		this.users = [{name: "user1", value:20}, {name :"user2", value:0}, {value:"user3", value:30}];
		this.echelle = 20;

		this.canvas = document.getElementById("mon_canvas");
		this.context = this.canvas.getContext("2d");

		this.xWing = new Image();
		this.xWing.src = 'img/xwing.gif';

		this.edlm = new Image();
		this.edlm.src = 'img/edlm.gif';

		this.yoda = new Image();
		this.yoda.src = 'img/yoda.gif';

		this.xWingUser = new Image();
		this.xWingUser.src = 'img/xwingUSER.gif';


		this.eraseCanvas = function(){
			this.context.clearRect(0, 0, canvas.width, canvas.height);
		}

		this.putPicturesOnCanvas = function(){
			if(this.GDtot === 0){
				this.context.drawImage(this.yoda, this.canvas.width/2 - this.yoda.width/2, this.canvas.height - this.yoda.height);
			}
			else{
				this.context.drawImage(this.edlm, this.canvas.width/2 - this.edlm.width/2, this.canvas.height - this.edlm.height);	
			}
			this.divMinHeightPosition = this.canvas.height - this.edlm.height - this.xWingUser.height
			
			j = 5;
			k = 0;
			
			this.context.drawImage(this.xWingUser, 15 + k + j, this.divMinHeightPosition * this.GDUser / this.echelle);	

			for(x of this.users){
				if( k === 0){
					j += 5;
					if(j === 10){
						j = -5;
					}
				}
				this.context.drawImage(this.xWing, 15 + k + j, this.divMinHeightPosition - (this.divMinHeightPosition * x.value / this.echelle));	
				k = (k+40)%160;
			}
		}

		this.refreshCanvas = function(){
			this.eraseCanvas();
			//TODO get values of group divergences.
			this.putPicturesOnCanvas();
		}

		this.putPicturesOnCanvas();
	});

