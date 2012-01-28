package se.arnetheduck.j2c.handlers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IPackageFragmentRoot;

import se.arnetheduck.j2c.transform.Transformer;

public class HandlerHelper {
	public static void process(IJavaProject project) throws Exception {
		process(project, null);
	}

	public static void process(IJavaProject project, ICompilationUnit unit)
			throws Exception {
		List<ICompilationUnit> units = new ArrayList<ICompilationUnit>();

		if (unit == null) {
			IPackageFragment[] packages = project.getPackageFragments();
			for (IPackageFragment mypackage : packages) {
				if (mypackage.getKind() == IPackageFragmentRoot.K_SOURCE) {
					for (ICompilationUnit u : mypackage.getCompilationUnits()) {
						units.add(u);
					}
				}
			}
		} else {
			units.add(unit);
		}

		Transformer t = new Transformer(project);

		// We will write to the source folder prefixed with "c"
		IPath p = project.getProject().getLocation().removeLastSegments(1)
				.append("c" + project.getProject().getLocation().lastSegment())
				.addTrailingSeparator();

		t.process(p, units.toArray(new ICompilationUnit[0]));
	}

	public static void process(ICompilationUnit unit) throws Exception {
		process(unit.getJavaProject(), unit);
	}
}