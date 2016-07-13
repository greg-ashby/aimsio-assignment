#aimsio-assignment


This is a sample web app for a job application with Aimsio. The goal is to show I can learn and use the Vaadin framework quickly and effectively.

#Notes
1. I assumed the focus was on java programming so I didn't do much with the application cosmetics (header, footer, css, etc). Happy to do more with that if desired though!
2. For **Interactivity**, I chose to add multiple series so a user can plot just about any combination they want. Just click the **Add New Series** button in the **Series Filter**, and/or **Edit** a series to play around with as desired. NOTE that when you add a new series, it won't show up on chart until you click **Update** in order to keep the UI snappy (rather than wait a few seconds for a default series to load before you can edit it).
3. All data for each series is loaded from the database with a query customized for it's filters, and then cached. This allows the chart to re-render quickly as it only needs to reload data for which ever series was updated.
4. For **Responsiveness**, the chart will resize it self based on the browser dimensions (within a min and max size), and reposition itself either beside or below the series filter section if the browser width is too small. Again, I chose to do that programmatically, but if you want to see more functionality or using more CSS methods, feel free to ask!
5. Also, I didn't do a whole lot of user input checking as I'm assuming all my users are well behaved and only interacting via the UI :). A malicious user could in theory execute calls on the server with unexpected inputs, but I don't think they could do much harm given a) the connection is configured on my server as read-only, and b) all the queries against the db are pre-canned or well structured with prepared-statements. But, yes, I am aware I should do more input checking and validation in a non-toy application :). Again, happy to do more if desired!  

**Bonus Question: Testing** 
1. I have included some JUnit tests already. This is my preferred method for testing. Mostly I focused on ensuring the QueryBuilder was working correctly as this was the most complicated (and possibly breakable) logic - i.e. constructing queries based on a number of different parameter options. I didn't bother writing tests for the database access layer since that's fairly straight forward... assuming the queries are correct (tested elsewhere), it just executes them and returns the results.
2. For the UI testing, I did create one mock object to ensure the PageResizeHandler was handling events correctly when determining when to update the UI. It would be possible to create more Mock objects and write more tests (again, happy to do so if desired), but otherwise I would look into using a browser automation tool (e.g. Selenium) to see if UI testing could be easily automated that way. (I haven't done that personally, but it's one of my next learning projects to tackle).

#Compiling and Running

To run this application:
1. create a mysql database and use the scripts contained in /database to load the sample data
2. mvn clean
3. mvn vaadin:compile
4. mvn install
5. deploy war file to Tomcat (or other servlet container)
6. ensure you have configured the jndi database connection in your servlet container (see below)
7. run it!

NOTE: You may need to install and procure a trial license for the Vaadin charts extension

Example JNDI configuration for Tomcat - put this in the <GlobalNamingResources> of server.xml

	<Resource name="com.gregashby.aimsio.database.DataSource"
			auth="Container" type="javax.sql.DataSource" username="{user}"
			password="{password}" driverClassName="com.mysql.jdbc.Driver"
			url="jdbc:mysql://{host}:{port}/{schema}" maxActive="100"
			maxIdle="30" maxWait="10000" validationQuery="SELECT count(*) FROM SIGNALS" />
			

#Code Overview
The application follows a standard Maven directory structure (with Vaadin specific files under /src/main/webapp/VAADIN). That is, source code is under /src/main/java, with some unit tests under /src/test/java. There's also a /database folder with the sample data and some datbase scripts for creating the necessary tables and loading the sample data.

**Package Overview**
(see individual class documentation for more details)
- com.gregashby.aismio.ui --> all the vaadin component related code for the user interface
- com.gregashby.aimsio.model --> main logic for managing the series that get drawn on the chart. Most of these classes have a getView() method that returns a CustomComponent for the UI layer, allowing the business logic and view to be kept fairly separate.
- com.gregashby.aimsio.database --> classes for reading from the database, and constructing queries from UI inputs
- com.gregashby.aismio.utils --> helper classes


#TODO
Things I didn't bother to do because it seemed like overkill, but if you'd like to see me tackle these (or any other additional changes), please feel free to ask!

- fix chart series so lines aren't drawn where there's missing data, instead of drawing a line between all points (as per https://vaadin.com/forum#!/thread/11655898, can use nulls to prevent lines being drawn, but I think this would require calculating all possible x-axis values based on the date-resolution, then inserting a null in the data series for each missing value. Can be done, I'm just choosing to be lazy and not doing it yet)
- add indexes to database to try to improve query times
- there's a small bug where the chart gets resized
