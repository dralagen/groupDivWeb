var app = angular.module("groupDiv.userController", []);

app.controller("userController", ['$scope', 'GApi', function($scope, GApi){
		
		$scope.tab = 1;
		
		$scope.sessionId = "5629499534213120";
		
		$scope.selectedUE;
		
		$scope.selectTab = function(setTab){
			$scope.tab = setTab;
		};
		
		$scope.isSelected = function(checkTab){
			return $scope.tab === checkTab;
		};
		
		$scope.GDtot = 0;
		$scope.currentUsr = {id: "5066549580791808", name: ""};
		$scope.users = [];

		$scope.UEOfCurrentUser = {};
		$scope.ues = [];
		$scope.echelle = 50;

		//here we get the id and the name of all the users. The same for ues
		GApi.execute('groupDivWeb', 'session.get', {sessionId: $scope.sessionId}).then(
			function(resp) {
				console.log("we get the session");
				$scope.ues = resp.ue;
				angular.forEach(resp.user, function(u){
					if(u.id == $scope.currentUsr.id){
						$scope.currentUsr[name] = u.name;
					}
					else{
						$scope.users.push(u);
					}
				});
			}, function() {
				console.log("We can't get the session");
			}
		);

		//here we get divergences values
		$scope.majDivergences = function(){
			GApi.execute('groupDivWeb', 'divergence', {sessionId: $scope.sessionId}).then(
				function(resp) {
					$scope.GDtot = resp.globalDivergence;
					angular.forEach(data.items, function(item){
						
					});
				}, function() {

				}
			);
		}
		
		$scope.majDivergences();
		
		

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
			$scope.divMinHeightPosition = $scope.canvas.height - $scope.edlm.height - $scope.xWingUser.height;
			
			j = 5;
			k = 0;
			
			for(x of $scope.users){
				if( k === 0){
					j += 5;
					if(j === 10){
						j = -5;
					}
				}
				console.log(x);
				console.log(x.id + "    " + $scope.currentUsr + "   " );
				console.log(x.id == $scope.currentUsr);
				if(x.id == $scope.currentUsr){
					$scope.context.drawImage($scope.xWingUser, 15 + k + j, $scope.divMinHeightPosition - ($scope.divMinHeightPosition * x.GD / $scope.echelle));
				}
				else{
					$scope.context.drawImage($scope.xWing, 15 + k + j, $scope.divMinHeightPosition - ($scope.divMinHeightPosition * x.GD / $scope.echelle));
				}
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
			GApi.execute('groupDivWeb', 'action.pull', {sessionId: $scope.sessionId, fromUserId: $scope.currentUsr.id, toUserId: userId}).then(
				function(resp) {
					console.log("pull sur : " + userId + "reussi");
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
