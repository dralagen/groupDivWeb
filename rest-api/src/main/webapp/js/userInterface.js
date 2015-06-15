var app = angular.module("groupDiv.userController", []);

app.controller("userController", ['$scope', function($scope){

		$scope.tab = 1;
		
		$scope.selectTab = function(setTab){
			$scope.tab = setTab;
		};
		
		$scope.isSelected = function(checkTab){
			return $scope.tab === checkTab;
		};
		
		$scope.GDtot = 56;
		$scope.GDUser = 10;
		$scope.ues = [{name: "ue1", id:"1"}, {name:"ue2", id:"2"}, {name:"ue3", id:"3"}];
		$scope.users = [{name: "user1", id:20}, {name :"user2", id:0}, {name:"user3", id:30}];
		$scope.echelle = 20;

		$scope.canvas = document.getElementById("mon_canvas");
		$scope.context = $scope.canvas.getContext("2d");

		$scope.xWing = new Image();
		$scope.xWing.src = 'img/xwing.gif';

		$scope.edlm = new Image();
		$scope.edlm.src = 'img/edlm.gif';

		$scope.yoda = new Image();
		$scope.yoda.src = 'img/yoda.gif';

		$scope.xWingUser = new Image();
		$scope.xWingUser.src = 'img/xwingUSER.gif';


		$scope.eraseCanvas = function(){
			$scope.context.clearRect(0, 0, canvas.width, canvas.height);
		}

		$scope.putPicturesOnCanvas = function(){
			if($scope.GDtot === 0){
				$scope.context.drawImage($scope.yoda, $scope.canvas.width/2 - $scope.yoda.width/2, $scope.canvas.height - $scope.yoda.height);
			}
			else{
				$scope.context.drawImage($scope.edlm, $scope.canvas.width/2 - $scope.edlm.width/2, $scope.canvas.height - $scope.edlm.height);	
			}
			$scope.divMinHeightPosition = $scope.canvas.height - $scope.edlm.height - $scope.xWingUser.height
			
			j = 5;
			k = 0;
			
			$scope.context.drawImage($scope.xWingUser, 15 + k + j, $scope.divMinHeightPosition * $scope.GDUser / $scope.echelle);	

			for(x of $scope.users){
				if( k === 0){
					j += 5;
					if(j === 10){
						j = -5;
					}
				}
				$scope.context.drawImage($scope.xWing, 15 + k + j, $scope.divMinHeightPosition - ($scope.divMinHeightPosition * x.value / $scope.echelle));	
				k = (k+40)%160;
			}
		}

		$scope.refreshCanvas = function(){
			$scope.eraseCanvas();
			//TODO get values of group divergences.
			$scope.putPicturesOnCanvas();
		}

		$scope.putPicturesOnCanvas();

		$scope.pullUsr = function(userName){
			console.log("pull sur : " + userName);
		}

		$scope.postReview = function(){
			console.log("post sur : " );
		}

		$scope.test = function(t){
			console.log(t);
		}
	}]);
