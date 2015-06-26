var app = angular.module("groupDiv.userController", []);

app.controller("userController", ['$scope', '$routeParams', 'GApi', 'Users', function($scope, $routeParams, GApi, Users){

	$scope.tab = 1;
	$scope.waitForPull = false;

	$scope.sessionId = $routeParams.sessionId;

	$scope.selectedUE = {};
	$scope.selectedUE.sel = [];
	$scope.reviews = {};

	$scope.current = {};
	$scope.current.usr = Users.currentUser;
	$scope.current.usr.id = $routeParams.userId;

	$scope.current.ue = {};

	$scope.users = Users.users;
	$scope.ues = [];

	//here we get the id and the name of all the users. The same for ues. It is an initialisation
	GApi.execute('groupDivWeb', 'session.get', {sessionId: $scope.sessionId}).then(
		function(resp) {
			console.log("we get the session");
			//get users
			angular.forEach(resp.user, function(usr){
				if(usr.id == $scope.current.usr.id){
					$scope.current.usr.name = usr.name;
				}
				$scope.users[usr.id] = usr;
				$scope.users[usr.id].groupDivergence = 0;
			});
			console.log("we get the users");

			//get ues
			angular.forEach(resp.ue, function(oneUe){
				temp = oneUe;
				if(oneUe.authorId == $scope.current.usr.id){
					$scope.current.ue = temp;
				}
				else{
					$scope.ues.push(temp);
				}
				$scope.reviews[temp.id] = [];
			});
			$scope.selectedUE.sel = $scope.ues[0];
			console.log("we get the ues");

		}, function(err) {
			console.log("We can't get the session : " + err.error.message);
		}
	);

	//to pull one user and get new informations
	$scope.pullUsr = function(userId){
		$scope.waitForPull = true;

		GApi.execute('groupDivWeb', 'action.pull', {sessionId: $scope.sessionId, fromUserId: $scope.current.usr.id, toUserId: userId}).then(
			function(resp) {

				console.log("pull on : " + userId + " successful");

				//get ues content
				angular.forEach(resp.ue, function(item){
					angular.forEach($scope.ues, function(ue){
						if(ue.id == item.id){
							ue.content = item.content;
						}
						else if($scope.current.ue.id == item.id){
							$scope.current.ue.content = item.content;
						}
					});
				});
				console.log("we get the versions of ues");

				//get reviews
				angular.forEach(resp.review, function(rev){
					$scope.reviews[rev.ueId].push(rev);
				});
				console.log("we get the new reviews");
				$scope.waitForPull = false;
			}, function(err) {
				console.log("error you can't pull : " + userId + " : " + err.error.message);
				$scope.waitForPull = false;
			}
		);

	}

	//initialisation of information for the current user.
	$scope.pullUsr($scope.current.usr.id);

	//to make available the review for all the users
	$scope.postReview = function(){
		GApi.execute('groupDivWeb', 'action.commit.review', {sessionId: $scope.sessionId, authorId: $scope.current.usr.id, ueId: $scope.selectedUE.sel.id, content: $scope.reviews.reviewToPost}).then(
			function(resp) {
				console.log("review post successful");
				newReview = {content:$scope.reviews.reviewToPost, authorId: $scope.current.usr.id, postDate: resp.date, ueId: $scope.selectedUE.sel.id};
				$scope.reviews[$scope.selectedUE.sel.id].push(newReview);
				$scope.reviews.reviewToPost = "";
			}, function(err) {
				console.log("error you can't post your review : " + err.error.message);
			}
		);
	}

	//to make available the version of the ue for all the users
	$scope.postUE = function(){
		GApi.execute('groupDivWeb', 'action.commit.ue', {sessionId: $scope.sessionId, authorId: $scope.current.usr.id, ueId: $scope.current.ue.id, content: $scope.current.ue.content}).then(
			function(resp) {
				console.log("post ue successful");
			}, function(err) {
				console.log("error you can't post your ue : " + err.error.message);
			}
		);
	}

	//to set the selected tab
	$scope.selectTab = function(setTab){
		$scope.tab = setTab;
	};

	//to see if a tab is selected
	$scope.isSelected = function(checkTab){
		return $scope.tab === checkTab;
	};

}]);

//to convert an object of objects into an array of objects
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
