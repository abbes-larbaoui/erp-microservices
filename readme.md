<h2><u>About PROJECT</u></h2>

This project implements a microservices architecture to create a scalable and maintainable information system that manages non-functional requirements,  It currently includes:

* Authentication: Keycloak
* Authorization (custom): Spring Boot and PostgreSQL
* Audit Logging: NestJS and MongoDB
* Notifications: Spring Boot and PostgreSQL
* API Gateway: Spring Cloud Gateway
* Discovery Server: Eureka
* Asynchronous Communication: Kafka

![alt text](https://github.com/abbes-larbaoui/erp-microservices/blob/master/ArchitectureDiagram_.jpg?raw=true)


<h2><u>ERP MICROSERVICES Specifications:</u></h2>

    1.  Admin MS: 

    2.  Audit MS:

    3.  Notification MS:

    4.  Config MS: (not yet)

    5.  Doc Manager MS: (not yet)

    6.  Reporting MS: (not yet)


1.  <h3><u>Admin (Authorization) MS:</u></h3>

    <!-- -->

    ![alt text](https://github.com/abbes-larbaoui/erp-microservices/blob/master/admin-service/admin-conception.png?raw=true)

    1.  <h4>Module Management:</h4>

        1.  The functionalities:

            <table>
            <colgroup>
            <col style="width: 7%" />
            <col style="width: 51%" />
            <col style="width: 30%" />
            <col style="width: 10%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th></th>
            <th><strong>The functionality</strong></th>
            <th><strong>actor</strong></th>
            <th><strong>code</strong></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>1</td>
            <td>Add a module</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>2</td>
            <td>View a module</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>3</td>
            <td>Modify a module in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>4</td>
            <td>Enable/Disable a module</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>5</td>
            <td>Delete a module in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>6</td>
            <td>List the modules</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>7</td>
            <td>Filter modules</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        2.  Fields:

            <table>
            <colgroup>
            <col style="width: 32%" />
            <col style="width: 13%" />
            <col style="width: 16%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th><strong>fields</strong></th>
            <th><strong>type</strong></th>
            <th><strong>Mandatory?</strong></th>
            <th></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>Module name</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Module code</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Color</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Icon</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Uri</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Active</td>
            <td>Boolean</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        3.  The business rules:

            1.  The module name must be unique.

            2.  The module code must be unique.

    <!-- -->

    2.  <h4>User Management:</h4>

        1.  The functionalities:

            <table>
            <colgroup>
            <col style="width: 7%" />
            <col style="width: 51%" />
            <col style="width: 30%" />
            <col style="width: 10%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th></th>
            <th><strong>The functionality</strong></th>
            <th><strong>actor</strong></th>
            <th><strong>code</strong></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>1</td>
            <td>Add a user</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>2</td>
            <td>View a user</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>3</td>
            <td>Edit a user in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>4</td>
            <td>Enable/Disable a user</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>5</td>
            <td>Delete a user in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>6</td>
            <td>Add a profile for a user</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td><span dir="rtl">7</span></td>
            <td>Change the default profile</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td><span dir="rtl">8</span></td>
            <td>Delete a profile for user</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td><span dir="rtl">9</span></td>
            <td>Add a module for profile</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td><span dir="rtl">10</span></td>
            <td>Remove a module for profile</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td><span dir="rtl">11</span></td>
            <td>Add a role for a profile</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td><span dir="rtl">12</span></td>
            <td>Remove a role for a profile</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td><span dir="rtl">13</span></td>
            <td>Add a privilege for a profile</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td><span dir="rtl">14</span></td>
            <td>Remove a privilege for a profile</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>15</td>
            <td>List users</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>16</td>
            <td>Filter users</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>17</td>
            <td>Add required actions</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>18</td>
            <td>List online users</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        2.  Fields:

            <table>
            <colgroup>
            <col style="width: 32%" />
            <col style="width: 13%" />
            <col style="width: 16%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th><strong>fields</strong></th>
            <th><strong>type</strong></th>
            <th><strong>Mandatory?</strong></th>
            <th></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>Email</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Telephone</td>
            <td>Text</td>
            <td>non</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Username</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>First Name</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Last name</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>List of profiles</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Default profile</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Active</td>
            <td>Boolean</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        3.  The business rules:

            1.  The email and username must be unique.

            2.  The user must have a minimum profile.

    <!-- -->

    3. <h4> User Group Management:</h4>

        1.  The functionalities:

            <table>
            <colgroup>
            <col style="width: 7%" />
            <col style="width: 51%" />
            <col style="width: 30%" />
            <col style="width: 10%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th></th>
            <th><strong>The functionality</strong></th>
            <th><strong>actor</strong></th>
            <th><strong>code</strong></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>1</td>
            <td>Add a group</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>2</td>
            <td>View a group</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>3</td>
            <td>Edit a group in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>4</td>
            <td>Enable/Disable a Group</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>5</td>
            <td>Delete a group in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>6</td>
            <td>List the groups</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>7</td>
            <td>Filter groups</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        2.  Fields:

            <table>
            <colgroup>
            <col style="width: 31%" />
            <col style="width: 13%" />
            <col style="width: 19%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th><strong>fields</strong></th>
            <th><strong>type</strong></th>
            <th><strong>Mandatory?</strong></th>
            <th></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>Libel</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Active</td>
            <td>Boolean</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        3.  The business rules:

            1.  Libel must be unique.


    <!-- -->
    
    4.  <h4>Privilege Management:</h4>

        1.  The functionalities:

            <table>
            <colgroup>
            <col style="width: 7%" />
            <col style="width: 51%" />
            <col style="width: 30%" />
            <col style="width: 10%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th></th>
            <th><strong>The functionality</strong></th>
            <th><strong>actor</strong></th>
            <th><strong>code</strong></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>1</td>
            <td>Add privilege</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>2</td>
            <td>View a privilege</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>3</td>
            <td>Modify a privilege in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>4</td>
            <td>Enable/Disable a Privilege</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>5</td>
            <td>Delete a privilege in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>6</td>
            <td>List privileges</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>7</td>
            <td>Filter privileges</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        2.  Fields:

            <table>
            <colgroup>
            <col style="width: 31%" />
            <col style="width: 13%" />
            <col style="width: 19%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th><strong>fields</strong></th>
            <th><strong>type</strong></th>
            <th><strong>Mandatory?</strong></th>
            <th></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>Libel</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Type</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Module</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Active</td>
            <td>Boolean</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        3.  The business rules:

            1.  Libel must be unique per module.

    <!-- -->

    5.  <h4>Role Management:</h4>

        1.  The functionalities:

            <table>
            <colgroup>
            <col style="width: 7%" />
            <col style="width: 51%" />
            <col style="width: 30%" />
            <col style="width: 10%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th></th>
            <th><strong>The functionality</strong></th>
            <th><strong>actor</strong></th>
            <th><strong>code</strong></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>1</td>
            <td>Add a role</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>2</td>
            <td>View a role</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>3</td>
            <td>Modify a role in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>4</td>
            <td>Enable/Disable a role</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>5</td>
            <td>Delete a role in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>6</td>
            <td>List the roles</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>7</td>
            <td>Filter roles</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        2.  Fields:

            <table>
            <colgroup>
            <col style="width: 31%" />
            <col style="width: 13%" />
            <col style="width: 19%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th><strong>fields</strong></th>
            <th><strong>type</strong></th>
            <th><strong>Mandatory?</strong></th>
            <th></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>Libel</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>List of privileges</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Module</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Active</td>
            <td>Boolean</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        3.  The business rules:

            1.  Libel must be unique per module.

            <!-- -->

2.  <h3><u>Audit Log MS:</u></h3>

    1.  The functionalities:

        <table>
        <colgroup>
        <col style="width: 7%" />
        <col style="width: 51%" />
        <col style="width: 30%" />
        <col style="width: 10%" />
        </colgroup>
        <thead>
        <tr class="header">
        <th></th>
        <th><strong>The functionality</strong></th>
        <th><strong>actor</strong></th>
        <th><strong>code</strong></th>
        </tr>
        </thead>
        <tbody>
        <tr class="odd">
        <td>1</td>
        <td>List the audit logs</td>
        <td></td>
        <td></td>
        </tr>
        <tr class="even">
        <td>2</td>
        <td>View an audit log</td>
        <td></td>
        <td></td>
        </tr>
        <tr class="odd">
        <td>3</td>
        <td></td>
        <td></td>
        <td></td>
        </tr>
        <tr class="even">
        <td>4</td>
        <td></td>
        <td></td>
        <td></td>
        </tr>
        <tr class="odd">
        <td>5</td>
        <td></td>
        <td></td>
        <td></td>
        </tr>
        <tr class="even">
        <td>6</td>
        <td></td>
        <td></td>
        <td></td>
        </tr>
        </tbody>
        </table>

    2.  Fields:
        <table>
        <colgroup>
        <col style="width: 32%" />
        <col style="width: 13%" />
        <col style="width: 16%" />
        <col style="width: 18%" />
        <col style="width: 18%" />
        </colgroup>
        <thead>
        <tr class="header">
        <th><strong>fields</strong></th>
        <th><strong>type</strong></th>
        <th><strong>Mandatory?</strong></th>
        <th></th>
        <th></th>
        </tr>
        </thead>
        <tbody>
        <tr class="odd">
        <td>Module name</td>
        <td>Text</td>
        <td>yes</td>
        <td></td>
        <td></td>
        </tr>
        <tr class="even">
        <td>Entity name</td>
        <td>Text</td>
        <td>yes</td>
        <td></td>
        <td></td>
        </tr>
        <tr class="odd">
        <td>Entity Id</td>
        <td>Text</td>
        <td>yes</td>
        <td></td>
        <td></td>
        </tr>
        <tr class="even">
        <td>User</td>
        <td>Text</td>
        <td>yes</td>
        <td></td>
        <td></td>
        </tr>
        <tr class="even">
        <td>User IP Address</td>
        <td>Text</td>
        <td>yes</td>
        <td></td>
        <td></td>
        </tr>
        <tr class="odd">
        <td>Action</td>
        <td>Text</td>
        <td>yes</td>
        <td></td>
        <td></td>
        </tr>
        <tr class="even">
        <td>Time</td>
        <td>Date</td>
        <td>yes</td>
        <td></td>
        <td></td>
        </tr>
        <tr class="odd">
        <td>Data</td>
        <td>Text</td>
        <td>yes</td>
        <td></td>
        <td></td>
        </tr>
        </tbody>
        </table>

    3.  The business rules:
        1.
        <!-- -->

3.  <h3><u>Notification MS:</u></h3>

    1.  <h4>Notification Management:<h4>

        1.  The functionalities:

            <table>
            <colgroup>
            <col style="width: 7%" />
            <col style="width: 51%" />
            <col style="width: 30%" />
            <col style="width: 10%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th></th>
            <th><strong>The functionality</strong></th>
            <th><strong>actor</strong></th>
            <th><strong>code</strong></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>1</td>
            <td>List notifications for a user</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>2</td>
            <td>Read a notification</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>3</td>
            <td>Delete a notification</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>4</td>
            <td>Send Email notification</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>5</td>
            <td>Send SMS notification</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>6</td>
            <td>Push IN-APP notification</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        2.  Fields:

            <table>
            <colgroup>
            <col style="width: 32%" />
            <col style="width: 13%" />
            <col style="width: 16%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th><strong>fields</strong></th>
            <th><strong>type</strong></th>
            <th><strong>Mandatory?</strong></th>
            <th></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>Notification type</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Subject</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Content</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Status</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Time</td>
            <td>Time</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        3.  The business rules:

            1.

    2.  <h4>Management of schedules notifications:</h4>

        1.  The functionalities:

            <table>
            <colgroup>
            <col style="width: 7%" />
            <col style="width: 51%" />
            <col style="width: 30%" />
            <col style="width: 10%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th></th>
            <th><strong>The functionality</strong></th>
            <th><strong>actor</strong></th>
            <th><strong>code</strong></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>1</td>
            <td>Create a schedule notification</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>2</td>
            <td>View a schedule notification</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>3</td>
            <td>Edit a schedule notification in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>4</td>
            <td>Delete a schedule notification in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>5</td>
            <td>List the notification schedules</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>6</td>
            <td>Filter the notification schedules</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>7</td>
            <td></td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        2.  Fields:

            <table>
            <colgroup>
            <col style="width: 32%" />
            <col style="width: 13%" />
            <col style="width: 16%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th><strong>fields</strong></th>
            <th><strong>type</strong></th>
            <th><strong>Mandatory?</strong></th>
            <th></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>Notification type</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Subject</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Content</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Status</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Notification date</td>
            <td>Date</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        3.  The business rules:

            1.  The notification date must be greater than the current date.

            <!-- -->

    3.  <h4>Management of notification templates:</h4>

        1.  The functionalities:

            <table>
            <colgroup>
            <col style="width: 7%" />
            <col style="width: 51%" />
            <col style="width: 30%" />
            <col style="width: 10%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th></th>
            <th><strong>The functionality</strong></th>
            <th><strong>actor</strong></th>
            <th><strong>code</strong></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>1</td>
            <td>Add a template</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>2</td>
            <td>View a template</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>3</td>
            <td>Edit a template in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>4</td>
            <td>Enable/Disable a template</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>5</td>
            <td>Delete a template in case of error</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>6</td>
            <td>List templates</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>7</td>
            <td>Filter templates</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        2.  Fields:

            <table>
            <colgroup>
            <col style="width: 31%" />
            <col style="width: 13%" />
            <col style="width: 19%" />
            <col style="width: 18%" />
            <col style="width: 18%" />
            </colgroup>
            <thead>
            <tr class="header">
            <th><strong>fields</strong></th>
            <th><strong>type</strong></th>
            <th><strong>Mandatory?</strong></th>
            <th></th>
            <th></th>
            </tr>
            </thead>
            <tbody>
            <tr class="odd">
            <td>Template code</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Subject</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="odd">
            <td>Content</td>
            <td>Text</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            <tr class="even">
            <td>Active</td>
            <td>Boolean</td>
            <td>yes</td>
            <td></td>
            <td></td>
            </tr>
            </tbody>
            </table>

        3.  The business rules:

            1.  The template code must be unique.
