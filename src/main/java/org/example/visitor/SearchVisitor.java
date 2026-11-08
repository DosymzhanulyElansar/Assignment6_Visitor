package org.example.visitor;

import java.util.*;

public class SearchVisitor implements Visitor {
    private final String targetName;
    private final long minSize;
    private final List<String> matchingPaths = new ArrayList<>();
    private final Deque<String> currentPath = new ArrayDeque<>();

    public SearchVisitor(String targetName, long minSize) {
        this.targetName = targetName;
        this.minSize = minSize;
    }

    @Override
    public void visit(FileNode file) {
        if (file.getName().contains(targetName) && file.getSize() >= minSize) {
            String path = String.join("/", currentPath);
            path = path.isEmpty() ? file.getName() : path + "/" + file.getName();
            matchingPaths.add(path);
        }
    }

    @Override
    public void visit(DirectoryNode directory) {
        currentPath.addLast(directory.getName());
        for (Element child : directory.getChildren()) {
            child.accept(this);
        }
        currentPath.removeLast();
    }

    public List<String> getMatchingPaths() {
        List<String> sorted = new ArrayList<>(matchingPaths);
        Collections.sort(sorted);
        return Collections.unmodifiableList(sorted);
    }
}