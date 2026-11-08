package org.example.visitor;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class VisitorPatternTest {
    private DirectoryNode root;
    private DirectoryNode subDir;
    private FileNode fileA;
    private FileNode fileB;

    @BeforeEach
    void setUp() {
        root = new DirectoryNode("root");
        subDir = new DirectoryNode("sub");
        fileA = new FileNode("test.txt", 100);
        fileB = new FileNode("data.csv", 300);

        subDir.addChild(fileA);
        root.addChild(subDir);
        root.addChild(fileB);
    }

    @Test // T1: Double dispatch
    void testDoubleDispatch() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        fileA.accept(visitor);
        assertEquals(100, visitor.getTotalSize());
    }

    @Test // T2: Size accumulation
    void testTotalSize() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        root.accept(visitor);
        assertEquals(400, visitor.getTotalSize());
    }

    @Test // T3: Combined search filter
    void testSearchFilter() {
        SearchVisitor visitor = new SearchVisitor("test", 50);
        root.accept(visitor);
        assertEquals(1, visitor.getMatchingPaths().size());
    }

    @Test // T4: Deterministic paths
    void testSearchPaths() {
        SearchVisitor visitor = new SearchVisitor("", 0);
        root.accept(visitor);
        List<String> paths = visitor.getMatchingPaths();
        assertEquals("root/data.csv", paths.get(0));
        assertEquals("root/sub/test.txt", paths.get(1));
    }

    @Test // T5: Statistics
    void testStatistics() {
        StatisticsVisitor visitor = new StatisticsVisitor();
        root.accept(visitor);
        assertEquals(2, visitor.getFileCount());
        assertEquals(2, visitor.getDirectoryCount());
        assertEquals(1, visitor.getMaxDepth());
    }

    @Test // T6: Empty structures & no matches
    void testEmptyAndNoMatch() {
        DirectoryNode empty = new DirectoryNode("empty");
        TotalSizeVisitor sizeV = new TotalSizeVisitor();
        empty.accept(sizeV);
        assertEquals(0, sizeV.getTotalSize());

        SearchVisitor searchV = new SearchVisitor("notFound", 0);
        root.accept(searchV);
        assertTrue(searchV.getMatchingPaths().isEmpty());
    }

    @Test // T7: State isolation / Repeat execution
    void testStateIsolation() {
        TotalSizeVisitor visitor = new TotalSizeVisitor();
        root.accept(visitor);
        long firstRun = visitor.getTotalSize();

        TotalSizeVisitor visitor2 = new TotalSizeVisitor();
        root.accept(visitor2);
        assertEquals(firstRun, visitor2.getTotalSize());
    }

    @Test // T8: Invalid mutations
    void testInvalidMutations() {
        assertThrows(IllegalArgumentException.class, () -> new FileNode("invalid.txt", -50));
        assertThrows(IllegalArgumentException.class, () -> root.addChild(new FileNode("sub", 10))); // duplicate name
    }
}
