package org.example.visitor;

public class Main {
    public static void main(String[] args) {
        DirectoryNode root = new DirectoryNode("root");
        DirectoryNode docs = new DirectoryNode("docs");

        FileNode file1 = new FileNode("report.pdf", 1024);
        FileNode file2 = new FileNode("data.csv", 2048);
        FileNode file3 = new FileNode("notes.txt", 512);

        docs.addChild(file1);
        docs.addChild(file2);
        root.addChild(docs);
        root.addChild(file3);

        TotalSizeVisitor sizeVisitor = new TotalSizeVisitor();
        root.accept(sizeVisitor);
        System.out.println("Total Size: " + sizeVisitor.getTotalSize() + " bytes");

        SearchVisitor searchVisitor = new SearchVisitor("data", 1000);
        root.accept(searchVisitor);
        System.out.println("Search Results: " + searchVisitor.getMatchingPaths());

        StatisticsVisitor statsVisitor = new StatisticsVisitor();
        root.accept(statsVisitor);
        System.out.println("Files: " + statsVisitor.getFileCount() +
                ", Directories: " + statsVisitor.getDirectoryCount() +
                ", Max Depth: " + statsVisitor.getMaxDepth());
    }
}