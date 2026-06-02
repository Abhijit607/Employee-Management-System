# GitHub Upload Steps

Use these commands after installing Git and creating a GitHub account.

## Option 1: Command Line

From inside the `employee-management-system` folder:

```bash
git init
git add .
git commit -m "Build employee management system using Java JDBC MySQL"
git branch -M main
git remote add origin https://github.com/YOUR_USERNAME/employee-management-system.git
git push -u origin main
```

Before running `git remote add`, create an empty repository on GitHub named:

```text
employee-management-system
```

Do not initialize the GitHub repository with README, `.gitignore`, or license because this project already includes local files.

## Option 2: GitHub Website

1. Go to GitHub.
2. Create a new repository named `employee-management-system`.
3. Open the repository.
4. Click **Add file**.
5. Click **Upload files**.
6. Upload all files from this project folder.
7. Commit the upload.

## Suggested Repository Description

```text
Core Java console application for managing employee records using JDBC and MySQL.
```

## Suggested GitHub Topics

```text
java
jdbc
mysql
sql
core-java
employee-management-system
console-application
```
