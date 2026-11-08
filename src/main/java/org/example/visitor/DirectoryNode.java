package org.example.visitor;

import java.util.*;

public class DirectoryNode implements Element {
    private final String name;
    private final List<Element> children = new ArrayList<>();
    private DirectoryNode parent;

    public DirectoryNode(String name) {
        this.name = name;
    }

    public void addChild(Element child) {
        for (Element e : children) {
            if (e.getName().equals(child.getName())) {
                throw new IllegalArgumentException("Duplicate sibling name: " + child.getName());
            }
        }

        if (child instanceof DirectoryNode dir) {
            if (dir == this || dir.hasAsParent(this)) {
                throw new IllegalArgumentException("Cycle detected");
            }
            if (dir.parent != null) {
                throw new IllegalArgumentException("Multiple parent ownership not allowed");
            }
            dir.parent = this;
        }

        children.add(child);
    }

    private boolean hasAsParent(DirectoryNode node) {
        DirectoryNode current = this.parent;
        while (current != null) {
            if (current == node) return true;
            current = current.parent;
        }
        return false;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override
    public String getName() {
        return name;
    }

    public List<Element> getChildren() {
        return Collections.unmodifiableList(children);
    }
}