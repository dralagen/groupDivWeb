var app = angular.module("groupDiv.userController", []);

app.controller("userController", ['$scope', 'GApi', function($scope, GApi){
		
		$scope.tab = 1;
		$scope.waitForPull = false;
		
		$scope.sessionId = "5629499534213120";

		$scope.selectedUE = {};
		$scope.selectedUE.sel = [];
		$scope.reviews = {};
		
		$scope.selectTab = function(setTab){
			$scope.tab = setTab;
		};
		
		$scope.isSelected = function(checkTab){
			return $scope.tab === checkTab;
		};
		
		$scope.currentUsr = {id: "5066549580791808", name: ""};
		$scope.currentUE = {};
		
		$scope.users = {};

		$scope.ues = [];

		//here we get the id and the name of all the users. The same for ues
		GApi.execute('groupDivWeb', 'session.get', {sessionId: $scope.sessionId}).then(
			function(resp) {
				console.log("we get the session");

				//get users
				angular.forEach(resp.user, function(usr){
					if(usr.id == $scope.currentUsr.id){
						$scope.currentUsr.name = usr.name;
					}
					$scope.users[usr.id] = usr;
				});

				//get ues
				angular.forEach(resp.ue, function(oneUe){
					temp = oneUe;
					if(oneUe.userId == $scope.currentUsr.id){
						$scope.currentUE = temp;
						console.log($scope.currentUE);
					}
					else{
						$scope.ues.push(temp);
					}
					$scope.reviews[temp.id] = [];
				});
				$scope.selectedUE.sel = $scope.ues[0];
				
			}, function() {
				console.log("We can't get the session");
			}
		);
		
		$scope.pullUsr = function(userId){
					$scope.waitForPull = true;
	
			GApi.execute('groupDivWeb', 'action.pull', {sessionId: $scope.sessionId, fromUserId: $scope.currentUsr.id, toUserId: userId}).then(
				function(resp) {
					console.log("pull sur : " + userId + " reussi");
					angular.forEach(resp.ue, function(item){
						angular.forEach($scope.ues, function(ue){
							if(ue.id == item.id){
								ue.content = item.content;
							}
							else if($scope.currentUE.id == item.id){
								$scope.currentUE.content = item.content;
							}
						});
					});
					//get reviews
					angular.forEach($scope.reviews, function(rev){
						rev.splice(0, rev.length);
					});
					console.log($scope.reviews);
					angular.forEach(resp.review, function(rev){
						$scope.reviews[rev.ueId].push(rev);
					});
			
				}, function() {
					console.log("error you can't pull : " + userId);
				}
			);
					$scope.waitForPull = false;

		}

		$scope.postReview = function(b){
			GApi.execute('groupDivWeb', 'action.commit.review', {sessionId: $scope.sessionId, authorId: $scope.currentUsr.id, ueId: $scope.selectedUE.sel.id, content: $scope.reviews.reviewToPost}).then(
				function(resp) {
					console.log("post review reussi");
					$scope.review.reviewToPost = "";
				}, function(err) {
					console.log(err);
					console.log($scope.reviewToPost);
					console.log("error you can't post your review .. ");
				}
			);
		}

		$scope.postUE = function(){

			GApi.execute('groupDivWeb', 'action.commit.ue', {sessionId: $scope.sessionId, authorId: $scope.currentUsr.id, ueId: $scope.currentUE.id, content: $scope.currentUE.content}).then(
				function(resp) {
					console.log("post ue reussi");
				}, function(err) {
					console.log("error you can't post your ue ");
				}
			);
		}

	}]);

app.filter('toArray', function () {
	return function (obj, addKey) {
		if (!obj) return obj;
		if ( addKey === false ) {
			return Object.keys(obj).map(function(key) {
				return obj[key];
			});
		} else {
			return Object.keys(obj).map(function (key) {
				return Object.defineProperty(obj[key], '$key', { enumerable: false, value: key});
			});
		}
	};
});
