(function(){
	var app = angular.module("Store", []);

	app.controller("StoreController", function() {
		this.products = gems;
	});
	
	app.controller("PanelController", function(){
		this.tab = 1;
		
		this.selectTab = function(setTab){
			this.tab = setTab;
		};
		
		this.isSelected = function(checkTab){
			return this.tab === checkTab;
		};
	});

	var gems = [
		{
			name: "Premier",
			price: 2.95,
			description: "c'est le premier !",
			reviews: [
				{
					body: "blabla",	
				},
				{
					body: "blabla2",	
					
				}
			]
		},
		{
			name: "Deuxième",
			price: 3.95,
			description: "c'est le deuxième !",
		}
	];

})();

function keydownFunction() {
    eraseCanvas();
}
