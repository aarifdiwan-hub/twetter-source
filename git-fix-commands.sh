# Check current branch name
git branch

# If you see "master" instead of "main", you need to rename it
# Method 1: Rename local branch from master to main
git branch -M main

# Then try pushing again
git push -u origin main

# If that doesn't work, try this alternative approach:
# Method 2: Create and switch to main branch, then push
git checkout -b main
git push -u origin main