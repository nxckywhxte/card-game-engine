#!/bin/sh

echo "🔧 Setting up Git hooks..."

# Копируем хуки в .git/hooks/
cp git-hooks/pre-commit .git/hooks/pre-commit
cp git-hooks/commit-msg .git/hooks/commit-msg

# Делаем их исполняемыми
chmod +x .git/hooks/pre-commit
chmod +x .git/hooks/commit-msg

echo "✅ Git hooks installed successfully!"
echo ""
echo "Hooks enabled:"
echo "  - pre-commit: runs Spotless check and tests"
echo "  - commit-msg: validates Conventional Commits format"