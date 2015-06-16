var app = angular.module("groupDiv.userController", []);

app.controller("userController", ['$scope', 'GApi', function myCC($scope, GApi){

		$scope.tab = 1;
		
		$scope.sessionId = "123456789";
		
		$scope.selectedUE;
		$scope.selectTab = function(setTab){
			$scope.tab = setTab;
		};
		
		$scope.isSelected = function(checkTab){
			return $scope.tab === checkTab;
		};
		
		$scope.GDtot = 56;
		$scope.currentUsr = {name: "me", id: 10, GD:"10"};
		$scope.users = [{name: "user1", id:20, GD:"10"}, {name :"user2", id:0, GD:"20"}, {name:"user3", id:30, GD:"50"}, {name:"user4", id:40, GD:"0"}, {name:"user5", id:50, GD:"5"}];

		$scope.UEOfCurrentUser = {name: "une ue", id: "uespe", content: "blablabla"};
		$scope.ues = [{name: "ue1", id:"u1", content: "je suis ue1"}, {name:"ue2", id:"u2", content: "je suis ue 2"}, {name:"ue3", id:"u3", content: "je suis ue 3"}];
		$scope.echelle = 50;

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
			
			$scope.context.drawImage($scope.xWingUser, 15 + k + j, $scope.divMinHeightPosition * $scope.currentUsr.GD / $scope.echelle);	

			for(x of $scope.users){
				if( k === 0){
					j += 5;
					if(j === 10){
						j = -5;
					}
				}
				console.log(x);
				console.log("   "+ k + "   "+ j);

				$scope.context.drawImage($scope.xWing, 15 + k + j, $scope.divMinHeightPosition - ($scope.divMinHeightPosition * x.GD / $scope.echelle));	
				k = (k+40)%160;
			}
		}

		$scope.refreshCanvas = function(){
			$scope.eraseCanvas();
			//TODO get values of group divergences.
			$scope.putPicturesOnCanvas();
		}

		$scope.putPicturesOnCanvas();

		$scope.pullUsr = function(userId){
			console.log("pull sur : " + userId);
		
			GApi.execute('groupDivWeb', 'action.pull', {userId: userId}).then(
				function(resp) {
					console.log(resp);
					//TODO use resp to update local data
				}, function() {
					console.log("error you can't pull : " + userId);
				}
			);
		}

		$scope.postReview = function(){
			console.log("post sur : " );
			//TODO add rest call
		}

		$scope.postUE = function(){
			console.log("post UE : " );
			//TODO add rest call
		}
	}]);
