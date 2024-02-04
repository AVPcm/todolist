<h1>requirements</h1>
Java Developer Task

Create a TODO-list project in Java (latest version). Front-end implementation itself is out of
scope of this task.

Implement API based backend for the features listed below. On the output we expect your
code on github or other platform where we can review it.

We should be able to build/run your project on Linux OS using corresponding JRE, Docker
and (if required) a package manager installed.

Please spend no more than 7 hours, then you can stop and share your interim or final
results. Please implement the features in the order they are listed below.

Must-have features:
1. Display a list of TODO items (each item must have a unique ID, title and description).
2. Add items to list.
3. Delete items from list.
4. Edit items.
5. Implement a multi-user environment. Each user must have a unique ID, username
(login) and password. Please use OAuth 2.0, otherwise you may encrypt user data
with username and password combination.

<h1>results</h1>
implemented 1-4, 5 partially implemented (self-written auth service)

I had no time to implement oauth2, but can do it (and docker part) on next iteration if you want

<h1>how to start</h1>

1. install java-21
2. clone project <code>git@github.com:AVPcm/todolist.git</code>
3. build/launch <code>../mvnw spring-boot:run</code>
4. open <code>http://127.0.0.1:8080</code>
5. navigate into swagger API documentation <code>/swagger-ui/index.html</code>
6. launch methods directly from swagger page or any other way you like to call http
7. * sample test scenario <code>com.github.avpcm.todo.TodoApplicationTests</code>