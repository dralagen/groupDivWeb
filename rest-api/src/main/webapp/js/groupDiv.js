var app = angular.module("groupDiv.groupDivController", []);

app.controller("groupDivController", ['$scope', 'GApi', 'Users', function($scope, GApi, Users){

	$scope.GDtot = 0;
	$scope.users = Users.users;
	$scope.currentUsr = Users.currentUser;

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
		$scope.context.clearRect(0, 0, $scope.canvas.width, $scope.canvas.height);
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

		//foreach angular hjs
		angular.forEach($scope.users, function(user){
			if( k === 0){
				j += 5;
				if(j === 10){
					j = -5;
				}
			}
			console.log(15 + k + j);
				console.log($scope.divMinHeightPosition - ($scope.divMinHeightPosition * user.GD / $scope.echelle));
				
			if(user.id == $scope.currentUsr.id){
				$scope.context.drawImage($scope.xWingUser, 15 + k + j, $scope.divMinHeightPosition - ($scope.divMinHeightPosition * user.GD / $scope.echelle));
			}
			else{
				$scope.context.drawImage($scope.xWing, 15 + k + j, $scope.divMinHeightPosition - ($scope.divMinHeightPosition * user.GD / $scope.echelle));
			}
			k = (k+40)%160;
		});
	}

	$scope.refreshCanvas = function(){
		$scope.eraseCanvas();
		//TODO get values of group divergences.
		$scope.putPicturesOnCanvas();
	}

	$scope.refreshCanvas();

	//here we get divergences values
	$scope.majDivergences = function(){
		/*GApi.execute('groupDivWeb', 'divergence', {sessionId: $scope.sessionId}).then(
			function(resp) {
				$scope.GDtot = resp.globalDivergence;
				angular.forEach(data.items, function(item){

				});
			}, function() {

			}
		);*/
	}

	$scope.majDivergences();
}]);

