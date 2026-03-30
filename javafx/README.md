# FXML Converter

- Converts the SVG icons to FXML files to be used with JavaFX.
- Displays the converted FXML files in a table.

See `./asseets/*/FXML`

## Setup after clone

Only required once:

    git remote add upstream https://github.com/microsoft/fluentui-system-icons.git

Check that it worked:

    git remote -v

This should be the result:

    origin    -> this fork
    upstream  -> original repo

## Convert updated icons

Make sure this is the main branch:

    git checkout main
    
Fetch the latest changes from the original repo:

    git fetch upstream

Merge into this main:

    git merge upstream/main

Push the updated main to this fork:

    git push origin main

Switch to the `javafx' feature branch:

    git checkout feature/javafx

Rebase onto the updated main:

    git rebase main

If conflicts arise, fix them, then continue:

    git add .
    git rebase --continue

Push the rebased branch:

    git push --force-with-lease origin feature/javafx