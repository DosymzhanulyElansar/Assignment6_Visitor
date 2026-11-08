package org.example.visitor;

public class StatisticsVisitor implements Visitor {
    private int fileCount = 0;
    private int directoryCount = 0;
    private int maxDepth = 0;
    private int currentDepth = 0;

    @Override
    public void visit(FileNode file) {
        fileCount++;
    }

    @Override
    public void visit(DirectoryNode directory) {
        directoryCount++;
        maxDepth = Math.max(maxDepth, currentDepth);

        currentDepth++;
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
        currentDepth--;
    }

    public int getFileCount() { return fileCount; }
    public int getDirectoryCount() { return directoryCount; }
    public int getMaxDepth() { return maxDepth; }
}