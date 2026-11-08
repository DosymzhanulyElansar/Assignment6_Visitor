package org.example.visitor;

public interface Visitor {
    void visit(FileNode file);
    void visit(DirectoryNode directory);
}