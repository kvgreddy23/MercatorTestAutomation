# Sample Page Object Framework#

This is a simple and generic page object framework, it can be used to automate any website.

## Running and Configuration ##

To add more test or to run on the local machine you need some basic set up

### Configuration: ###

1. Install latest Java Development Kit (JDK) (At the moment this project is using JDK 1.8)

2. Install Maven

3. Use one of the Eclipse or Intellij IDE for creating more test scripts

4. Install git

### How to get the project to your local machine ###

1. To get the project on to your local machine you need to have bitbucket account and should be a member of national trust team

2. Clone the project using git

Command: git clone https://yourusername@bitbucket.org/nationaltrust/website-frontend-tests.git

(or)

copy the url from top right hand corner where it says HTTPS

3. Use maven command to convert the project either a eclipse or intellij project

Command: mvn eclipse:eclipse (or) mvn idea:idea 

4. Open the project in the IDE 

### Dependencies ###

Project related dependencies and versions are managed by Maven

### How to run tests ###
Once you get the project on to local machine you can use either maven or testng to run the tests

### Git commands ###

1 Clone the project to your local machine
2 Create a local branch (git branch <Branch name>)
3 Checkout to the new branch (git checkout branch name)
4 Push the branch to remote repository (git push -u origin <Branchname>)
5 Work on the branch(git commit -am"message")
6 Push the changes to remote respository (git push)
7 Create a pull request
8 After approval merge the changes
9 On local machine checkout to the main branch(Develop branch) and pull the changes (git pull)