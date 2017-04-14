/*  ==============================================================================                                                        
 *                                                                                                                                        
 * Restaurant Paging App: Distributed Node Server                                                                                                                                                                           
 *                                                                                                                                        
 * ==============================================================================                                                         
 */                                                                                                   
var express = require('express');
var http = require('http');

// Socket.io allows for network streaming between connected                                                                               
// clients. It's a convenient way to broadcast data to specific                                                                           
// clients and also implement real-time services, such as chat                                                                            
var socketio = require('socket.io');
                                                                                           
var bodyParser = require('body-parser');

// The main instanced class, called app will be initialized by express                                                                    
var app = express(),
	httpServer = http.createServer(app),
	networkIORef = socketio.listen(httpServer);
	

// Much of the Socket.io use in this example is derived from                                                                              
//   https://socket.io/get-started/chat/                                                                                                  

// Set the port in the app system                                                                                                         
app.set("port", 4532);

app.use(bodyParser.urlencoded({   // support encoded bodies                                                                               
            extended: true
                }));
app.use(bodyParser.json());  // support json encoded bodies

//Start the app and let it listen for connections                                                                                         
httpServer.listen(app.get("port"), function () {
        console.log('Node app listening on port: ', app.get("port"));
    });

//Socket.io handler                                                                                                                       
networkIORef.on('connection', function(socket) {
        console.log('user connected');

        socket.on('log message', function(msg){
                networkIORef.emit('log message', msg);
            });
        socket.on('disconnect', function(){
                console.log('user now disconnected');
            });
    });

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

        response.writeHead(200, {'Content-Type': 'text/html'});

        /* Each write statement in the response can send text/data into the body                                                          
         * of the HTTP Response body */
        response.write('<!DOCTYPE html><head><title>Client DashBoard</title></head><body>');
        response.write('<H1>Restaurant Paging App</H1>');
        response.write(JSON.stringify(array));

        response.write('</body></html>');

        response.end();

        /* Occasional console output can be helpful for debugging too. */
        console.log('Received Dashboard request!');
        getReservation.reservationArray.push(array);

    });

/* GET - retrieves data from server */
app.get('/getData', function (request, response) {
        res.sendFile(__dirname + '/baseHTML.html');
    });

/* POST - sends data to server */
var postCount = 0;
var array = {
	waitList : []
}
var reservation = {
	"name" : "",
	"size" : "",
	"phoneNumber" : "",
	"time" : ""
};
app.post('/postReservation', function (request, response) {

    if (!request.body) return response.sendStatus(400);
	postCount++;
	
	
	reservation.name = request.body.name,
	reservation.size = request.body.size,
	reservation.phoneNumber = request.body.phoneNumber,
	reservation.time = request.body.time
	
	
	
	//Posting to the array 
	array.waitList.push(name);
	array.waitlist.push(size);
	array.waitlist.push(phoneNumber);
	array.waitlist.push(time);
	
        // Get a timestamp that we can use to seeing ordered log messages                                                                 
        var timestamp = new Date().valueOf();

        var logstr = '';
        for(var elemName in req.body) {
            logstr = logstr + "[" + elemName + ": " + req.body[elemName] + "] ";
        }

        networkIORef.emit('log message', timestamp + ': Received /postReservation POST' + logstr);


        res.status(200).send('OK');
        
        res.end();
    })

/* PUT - sends data to server */
app.put('/putReservation', function (request, response) {
        console.log('PUT REQUEST: putData');
 });

/* DELETE - removes data from server */
app.delete('/deleteData', function (request, response) {
        console.log('DELETE REQUEST: Data has been deleted.');
    });

/* Functions */
//networkIORef.emit('name', name);
//networkIORef.emit('size', size);
//networkIORef.emit('phoneNumber', phoneNumber);
//networkIORef.emit('time', time);
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
        request.status(404).send('Sorry, cant find that page!');
    });

app.use(function(error, request, response, next){
        console.error(error.stack);
		request.status(500).send('Something broke!');
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
