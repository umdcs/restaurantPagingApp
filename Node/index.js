/*  ==============================================================================                                                        
 *                                                                                                                                        
 * Restaurant Paging App: Distributed Node Server                                                                                                                                                                           
 *                                                                                                                                        
 * ==============================================================================                                                         
 */                                                                                                   
var express = require('express');
var http = require('http');


var app = express();

app.set("port", 4532);

var bodyParser = require('body-parser');

app.use(bodyParser.urlencoded({   // support encoded bodies                                                                               
            extended: true
                }));
app.use(bodyParser.json());  // support json encoded bodies

var postCount = 0;

var stages = {
    waitList : [],
    seatedList : []
}


/* -------------------------------------------------                                                                                      
 * ROUTE Descriptions                                                                                                                     
 *                                                                                                                                        
 * This is where you process the GET, POST, PUT, DELETE and other                                                                         
 * potential routes you might need for your API.                                                                                          
 *                                                                                                                                        
 * Default route -- i.e. the Dashboard for you app...                                                                                     
 */
 
 //GET REQUESTS//
app.get('/', function(request, response) {
	console.log('Beginning of app.get /');
        response.writeHead(200, {'Content-Type': 'text/html'});

        /* Each write statement in the response can send text/data into the body                                                          
         * of the HTTP Response body */
        response.write('<!DOCTYPE html><head><title>Client DashBoard</title></head><body>');
        response.write('<H1>Restaurant Paging App</H1>');

        response.write(JSON.stringify(stages));

        response.write('</body></html>');

        response.end();

        /* Occasional console output can be helpful for debugging too. */
        console.log('Received Dashboard request!');

    });

app.get('/getData', function(request, response) {

        response.json(stages);
        
        response.end();

        /* Occasional console output can be helpful for debugging too. */
        console.log('Received waitinglist request!');

    });

/* POST - sends data to server */

app.post('/postReservation', function (request, response) {


	console.log('beginning of postReservation');
    if (!request.body) return response.sendStatus(400);
	postCount++;
	
    var body = request.body;
    var reservation = {};

    response.write("<H1>Name</H1>")
    reservation.name = body.name;
    reservation.size = body.size;
    reservation.phoneNumber = body.phoneNumber;
    reservation.time = body.time;
    reservation.options = body.options;
    reservation.otherRequests = body.otherRequests;

    console.log(reservation);


	if(body.isSeated){
        stages.seatedList.push(reservation);
    }
    else{
        //Posting to the array 
        stages.waitList.push(reservation);
    }

        // Get a timestamp that we can use to seeing ordered log messages                                                                 
        var timestamp = new Date().valueOf();
        
        response.end();
    })


/* PUT - sends data to server */
app.put('/putReservation', function (request, response) {

        console.log('PUT REQUEST: putData');
 });


// Only deletes from waitList right now
/* DELETE - removes data from server */
app.delete('/deleteData', function (request, response) {

        var body = request.body;    // We don't directly set deleteReservation = request.body because the body includes extra information like isSeated()
        var deleteReservation = {};
        deleteReservation.name = body.name;
        deleteReservation.size = body.size;
        deleteReservation.phoneNumber = body.phoneNumber;
        deleteReservation.time = body.time;
        deleteReservation.options = body.options;
        deleteReservation.otherRequests = body.otherRequests;
        
        console.log(deleteReservation);


        if(body.isSeated){
            for (var i = 0; i < stages.seatedList.length; i++) {
                var res = stages.seatedList[i];
                if(JSON.stringify(res) === JSON.stringify(deleteReservation)){
                    console.log("Deleted from seated");
                    stages.seatedList.splice(i,1);
                    break;
                }
            };
        }
        else{
            for (var i = 0; i < stages.waitList.length; i++) {
                var res = stages.waitList[i];
                if(JSON.stringify(res) === JSON.stringify(deleteReservation)){
                    console.log("Deleted from seated");
                    stages.waitList.splice(i,1);
                    break;
                }
            };
        }
        response.end();
        console.log('DELETE REQUEST: Data has been deleted.');
    });


/* ================================================                                                                                       
 * ================================================                                                                                       
 * ================================================                                                                                       
 *                                                                                                                                        
 * Error handeling section                                                                                                                
 *                                                                                                                                        
 * 404- Missing page handler                                                                                                              
 *                                                                                                                                        
 * 500- Broken Page Error                                                                                                                 
 * ================================================                                                                                       
 * ================================================                                                                                       
 * ================================================                                                                                       
 */

app.use(function(request, response, next){
        response.status(404).send('Sorry, cant find that page!');
    });

app.use(function(error, request, response, next){
        console.error(error.stack);
		response.status(500).send('Something broke!');
    });

/* ================================================                                                                                       
 * ================================================                                                                                       
 * ================================================                                                                                       
 *                                                                                                                                        
 * FINALLY, start the app and let it listen for connections on the                                                                        
 * network                                                                                                                                
 *                                                                                                                                        
 * This really needs to be last.                                                                                                          
 *                                                                                                                                        
 * app.listen opens up a network socket on port "port" and waits for                                                                      
 * HTTP connections                                                                                                                       
 *                                                                                                                                        
 * ================================================                                                                                       
 * ================================================                                                                                       
 * ================================================                                                                                       
 */


app.listen(app.get("port"), function () {
        console.log('Restaurant Paging System: Node app listening on port: ', app.get("port"));
    });
