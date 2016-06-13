angular.module('myApp').controller('searchFlights', function($scope,$http, $filter) {
$scope.flights=[];
$scope.flight={};
$scope.connflight={};
$scope.status=true
    $scope.getflights = function(flight){
        if(flight.connFlight!=true)
        {status=false}
        else{status=true}
    $http({
        method: 'POST',
        url: 'http://localhost:9000/homepage',
        headers: {'Content-Type': 'application/x-www-form-urlencoded'},
         transformRequest: function(obj) {
                var str = [];
                for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                return str.join("&");
            },
        data: {
        deploc: flight.dep,
        arrloc: flight.arr,
        date: $filter('date')(flight.date, "dd/MM/yyyy"),
        sortchoice: flight.choice,
        connflightstatus:status
        },
    }).success(
                function(result){
                    $scope.flights=result.list;
                    $scope.flight={};
                    $scope.error = "";
    }).error(
                function(data,statusText,headers)
                    {
                        $scope.error = "Error:"+data;
                    });
    };
    $scope.getconnFlights = function(flight){
     $http({
            method: 'PUT',
            url: 'http://localhost:9000/homepage/connFlights',
            headers: {'Content-Type': 'application/x-www-form-urlencoded'},
             transformRequest: function(obj) {
                    var str = [];
                    for(var p in obj)
                    str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
                    return str.join("&");
                },
            data: {
            name:flight.name
            },
        }).success(
                    function(result){
                        $scope.flights=result.list;
        }).error(
                    function(data,statusText,headers)
                        {
                            $scope.error = "Error:"+data;
                        });
    }
});