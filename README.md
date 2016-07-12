aimsio-assignment
==============

Template for a simple Vaadin application that only requires a Servlet 3.0 container to run.


TODO
==============
- fix chart series so lines aren't drawn where there's missing data, instead of drawing a line between all points (as per https://vaadin.com/forum#!/thread/11655898, can use nulls to prevent lines being drawn, but I think this would require calculating all possible x-axis values based on the date-resolution, then inserting a null in the data series for each missing value. Can be done, I'm just choosing to be lazy and not doing it yet)
- make chart render each series after loading data so users can see progress... just need to refactor updateChart and loadAllData so each data series gets drawn before loading the next one (or better yet, have each series loaded in a separate thread and drawn immediately after retrieval... again, I'm just chosing to be lazy and not optimize the interface that much yet).
- change "Add Series" button to provide a popup that let's user specify Name and Color, and add an 'Edit' button for each series filter to let them edit that.
- add indexes to database to try to improve query times
